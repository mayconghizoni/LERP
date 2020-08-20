package br.com.jdbc;

import br.com.jdbcinterface.EmprestimoDAO;
import br.com.modelo.Emprestimo;

import java.sql.*;

public class JDBCEmprestimoDAO implements EmprestimoDAO {
    private Connection conexao;

    public JDBCEmprestimoDAO (Connection conexao){
        this.conexao = conexao;
    }

    @Override
    public boolean inserir(Emprestimo emprestimo) {

        String comando = "INSERT INTO emprestimos (leitor_idleitor, exemplar_idexemplar, dataSaida) VALUES (? , ? , ?);";

        PreparedStatement p;

        try {

            p = this.conexao.prepareStatement(comando);

            p.setInt(1, emprestimo.getIdLeitor());
            p.setInt(2, emprestimo.getIdLivro());
            p.setString(3, emprestimo.getDataSaída());

            p.execute();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean verificaExistenciaLeitor(int id) {

        String comando = "SELECT * from leitor WHERE idleitor = "+id+";";

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            if(rs.next()){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return false;
    }

    @Override
    public boolean verificaExistenciaLivro(int id) {

        String comando = "SELECT * from exemplares WHERE idexemplares = "+id+" AND status = 1;";

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            if(rs.next()){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return false;
    }
}
