package br.com.jdbc;

import br.com.modelo.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCCategoriaDAO {

    private Connection conexao;

    public JDBCCategoriaDAO (Connection conexao){
        this.conexao = conexao;
    }

    public boolean inserir(Categoria categoria){

        String comando = "INSERT INTO categoria (idcategoria, descricao) VALUES (?, ?)";

        PreparedStatement p;

        try{
            p = this.conexao.prepareStatement(comando);

            p.setInt(1, categoria.getId());
            p.setString(2, categoria.getNome());

            p.execute();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }
}
