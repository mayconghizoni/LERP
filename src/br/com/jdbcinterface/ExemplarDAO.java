package br.com.jdbcinterface;

import br.com.modelo.Exemplar;

import java.util.List;

public interface ExemplarDAO {

    public boolean inserir(Exemplar exemplar);
    public List<Exemplar> buscar(int status);
    public boolean manutencao(int id);

}
