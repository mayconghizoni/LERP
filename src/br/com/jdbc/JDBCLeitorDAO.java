package br.com.jdbc;

import br.com.jdbcinterface.LeitorDAO;
import br.com.modelo.Leitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCLeitorDAO implements LeitorDAO {

    private Connection conexao;

    public JDBCLeitorDAO (Connection conexao){
        this.conexao = conexao;
    }

    @Override
    public boolean inserir(Leitor leitor) {

        String comando = "INSERT INTO leitor (idleitor, nome, telefone, endereco, cpf) VALUES (?,?,?,?,?)";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);

            p.setInt(1,leitor.getId());
            p.setString(2, leitor.getNome());
            p.setString(3, leitor.getFone());
            p.setString(4, leitor.getEndereco());
            p.setDouble(5,leitor.getCpf());

            p.execute();

            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }
}
