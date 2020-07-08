package br.com.jdbcinterface;

import br.com.modelo.Usuario;

import java.util.List;

public interface UsuarioDAO {

    public List<Usuario> buscar();
    
}
