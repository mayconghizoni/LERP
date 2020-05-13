package br.com.jdbc;

import br.com.modelo.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Categoria> buscar(){

        String comando = "SELECT * FROM categoria ORDER BY descricao ASC;";

        List<Categoria> listaCategoria = new ArrayList<Categoria>();

        Categoria categoria = null;

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while(rs.next()){
                categoria = new Categoria();
                categoria.setId(rs.getInt("idcategoria"));
                categoria.setNome(rs.getString("descricao"));

                listaCategoria.add(categoria);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return listaCategoria;
    }

}
