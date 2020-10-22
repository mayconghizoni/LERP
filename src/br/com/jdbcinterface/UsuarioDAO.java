package br.com.jdbcinterface;

import br.com.modelo.Usuario;

import java.util.List;

public interface UsuarioDAO {

    public List<Usuario> buscar(int status);
    public List<Usuario> buscarAtivos(int offset);
    public List<Usuario> buscarInativos(int offset);
    public boolean inserir(Usuario usuario);
    public boolean validarLogin(String usuario, String senha);
    public Usuario buscarPorId(int id);
    public boolean alterar(Usuario usuario);
    public boolean inativar(int id);
    public boolean ativar(int id);
}
