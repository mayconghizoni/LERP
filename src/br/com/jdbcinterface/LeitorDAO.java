package br.com.jdbcinterface;

import br.com.modelo.Leitor;

import java.util.List;

public interface LeitorDAO {

    public boolean inserir(Leitor leitor);
    public List<Leitor> buscar(int status);
    public List<Leitor> buscarMultados(int multa);
    public List<Leitor> buscarAtivos(int offset);
    public List<Leitor> buscarInativos(int offset);
    public List<Leitor> buscarComMultas(int offset);
    public Leitor buscarPorId(int id);
    public boolean alterar(Leitor leitor);
    public boolean inativar(int id);
    public boolean ativar(int id);
    public List<Leitor> buscarPorNome(String valorBusca);
    public void adicionarMulta (double valor, int idLeitor);
    public boolean pagarTotal(int id);
    public void inserirValor(Double valor);
    public boolean verificaValor(int id);
    public double pegarDivida(int id);
    public boolean atualizarMulta(int id, double valor);

}
