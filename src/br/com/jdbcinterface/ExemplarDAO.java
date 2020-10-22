package br.com.jdbcinterface;

import br.com.modelo.Exemplar;

import java.util.List;

public interface ExemplarDAO {

    public boolean inserir(Exemplar exemplar);
    public List<Exemplar> buscar(int status);
    public List<Exemplar> buscarAtivos(int offset);
    public List<Exemplar> buscarInativos(int offset);
    public List<Exemplar> buscarPorNome(String nome);
    public boolean ativarManutencao(int id);
    public boolean desativarManutencao(int id);
    public boolean deletar(int id);

}
