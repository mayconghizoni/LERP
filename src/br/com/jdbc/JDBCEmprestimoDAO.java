package br.com.jdbc;

import br.com.jdbcinterface.EmprestimoDAO;
import br.com.modelo.Emprestimo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JDBCEmprestimoDAO implements EmprestimoDAO {
    private Connection conexao;

    public JDBCEmprestimoDAO (Connection conexao){
        this.conexao = conexao;
    }

    @Override
    public boolean inserir(Emprestimo emprestimo) {

        String comando = "INSERT INTO emprestimos (leitor_idleitor, exemplar_idexemplar, dataSaida, dataDev) VALUES (? , ? , ?, ?);";

        PreparedStatement p;

        try {

            p = this.conexao.prepareStatement(comando);

            p.setInt(1, emprestimo.getIdLeitor());
            p.setInt(2, emprestimo.getIdLivro());
            p.setString(3, emprestimo.getDataSaida());
            p.setString(4, emprestimo.getDataDev());

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

    @Override
    public List<Emprestimo> buscar() {

        String comando = "SELECT emp.*, l.nome, ex.titulo  FROM emprestimos as emp INNER JOIN leitor as l on leitor_idleitor = idleitor inner join exemplares as ex on exemplar_idexemplar = idexemplares ORDER BY dataSaida ASC;";

        List<Emprestimo> listaEmprestimos = new ArrayList<Emprestimo>();

        Emprestimo emprestimo = null;

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while (rs.next()){

                emprestimo = new Emprestimo();

                emprestimo.setId(rs.getInt("idemprestimos"));
                emprestimo.setIdLivro(rs.getInt("exemplar_idexemplar"));
                emprestimo.setIdLeitor(rs.getInt("leitor_idleitor"));
                emprestimo.setDataSaida(rs.getString("dataSaida"));
                emprestimo.setDataDev(rs.getString("datadev"));
                emprestimo.setValorMulta(rs.getDouble("multaArrecadada"));
                emprestimo.setNomeLeitor(rs.getString("nome"));
                emprestimo.setTituloExemplar(rs.getString("titulo"));

                listaEmprestimos.add(emprestimo);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return listaEmprestimos;

    }

    @Override
    public boolean finalizar(int id) {
        String comando = "DELETE FROM emprestimos WHERE idemprestimos = ?;";
        PreparedStatement p;

        try {

            p = this.conexao.prepareStatement(comando);
            p.setInt(1, id);
            p.execute();

        }catch(SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    public boolean maisSete(Emprestimo emprestimo) {

        String comando = "UPDATE emprestimos SET dataDev = ? WHERE idemprestimos = ?;";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);
            p.setString(1, emprestimo.getDataDev());
            p.setInt(2, emprestimo.getId());

            p.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
