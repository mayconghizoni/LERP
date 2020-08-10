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
                usuario.setStatus(rs.getInt("status"));


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

            String comando = "SELECT idusuario FROM usuario WHERE idusuario = '" + usuario + "' AND senha = '" + senha + "' AND status = 0;";
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

    @Override
    public Usuario buscarPorId(int id) {

        String comando = "SELECT * FROM usuario WHERE idusuario = ?";
        Usuario usuario = new Usuario();

        try{

            PreparedStatement p = this.conexao.prepareStatement(comando);
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();

            if(rs.next()){

                usuario.setId(rs.getInt("idusuario"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setEmail(rs.getString("email"));
                usuario.setAcesso(rs.getInt("idacesso"));
                usuario.setStatus(rs.getInt("status"));

            }

            return usuario;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean alterar(Usuario usuario) {

        String comando = "UPDATE usuario SET nome=?, email=? ";

        if(!usuario.getSenha().equals("")){
            comando += ",senha=? ";
        }

        comando += "WHERE idusuario=?;";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);

            p.setString(1, usuario.getNome());
            p.setString(2,usuario.getEmail());
            if(!usuario.getSenha().equals("")){
                p.setString(3,usuario.getSenha());
                p.setInt(4,usuario.getId());
            }else{
                p.setInt(3,usuario.getId());
            }

            p.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean inativar(int id) {

        String comando = "UPDATE usuario SET status=? WHERE idusuario=?";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);

            p.setInt(1, 1);
            p.setInt(2, id);

            p.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean ativar(int id) {

        String comando = "UPDATE usuario SET status=? WHERE idusuario=?";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);

            p.setInt(1, 0);
            p.setInt(2, id);

            p.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }


}
