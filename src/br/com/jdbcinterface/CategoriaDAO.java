package br.com.jdbcinterface;

import br.com.modelo.Categoria;

import java.util.List;

public interface CategoriaDAO {

    public boolean inserir(Categoria categoria);
    public List<Categoria> buscar();

}