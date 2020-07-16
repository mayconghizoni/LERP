package br.com.jdbc;

import br.com.jdbcinterface.UsuarioDAO;
import br.com.modelo.Usuario;

import java.sql.*;
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

    @Override
    public boolean inserir(Usuario usuario) {

        String comando = "INSERT INTO usuario (nome, email, senha, idacesso) VALUES (?,?,?,?)";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);

            p.setString(1,usuario.getNome());
            p.setString(2, usuario.getEmail());
            p.setString(3, usuario.getSenha());
            p.setInt(4, usuario.getAcesso());

            p.execute();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean validarLogin(String usuario, String senha) {

        try {

            String comando = "SELECT idusuario FROM usuario WHERE idusuario = '" + usuario + "' AND senha = '" + senha + "';";
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            if (rs.next()) {
                return true;
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
