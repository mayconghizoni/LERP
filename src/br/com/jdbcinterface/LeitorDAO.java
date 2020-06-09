package br.com.jdbcinterface;

import br.com.modelo.Leitor;

import java.util.List;

public interface LeitorDAO {

    public boolean inserir(Leitor leitor);
    public List<Leitor> buscar();
    public Leitor buscarPorId(int id);


}
