package br.com.jdbc;

import br.com.jdbcinterface.UsuarioDAO;
import br.com.modelo.Usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCUsuarioDAO implements UsuarioDAO {

    private Connection conexao;

    public JDBCUsuarioDAO (Connection conexao){
        this.conexao = conexao;
    }

    @Override
    public List<Usuario> buscar() {

        String comando = "SELECT * FROM usuario ORDER BY nome ASC;";

        List<Usuario> listaUsuarios = new ArrayList<Usuario>();

        Usuario usuario = null;

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while(rs.next()){

                usuario = new Usuario();

                usuario.setId(rs.getInt("idusuario"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setEmail(rs.getString("email"));


                listaUsuarios.add(usuario);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return listaUsuarios;
    }
}
