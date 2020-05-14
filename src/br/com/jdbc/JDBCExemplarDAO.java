package br.com.jdbc;

import br.com.jdbcinterface.ExemplarDAO;
import br.com.modelo.Exemplar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCExemplarDAO implements ExemplarDAO {

    private Connection conexao;

    public JDBCExemplarDAO (Connection conexao){
        this.conexao = conexao;
    }

    @Override
    public boolean inserir(Exemplar exemplar) {

        String comando = "INSERT INTO exemplares (idexemplares, titulo, autor, ano, edicao, idcategoria) VALUES (?,?,?,?,?,?)";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);

            p.setInt(1, exemplar.getId());
            p.setString(2, exemplar.getTitulo());
            p.setString(3, exemplar.getAutor());
            p.setString(4, exemplar.getAno());
            p.setString(5, exemplar.getEdicao());
            p.setInt(6,exemplar.getCategoria());

            p.execute();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }
}
