package br.com.jdbcinterface;

import br.com.modelo.Exemplar;
import br.com.modelo.Relatorio;

import java.util.List;

public interface RelatorioDAO {

    public List<Relatorio> buscar(String dataInicio, String dataFim);

}
