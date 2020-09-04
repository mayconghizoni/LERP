package br.com.jdbcinterface;

import br.com.modelo.Emprestimo;

import java.util.List;
import java.util.Date;

public interface EmprestimoDAO {

    public boolean inserir(Emprestimo emprestimo);
    public boolean verificaExistenciaLeitor(int id);
    public boolean verificaExistenciaLivro(int id);
    public List<Emprestimo> buscar();
    public boolean finalizar(int id);
    public boolean maisSete(Emprestimo emprestimo);
    public boolean verificaQuantidade(int idLeitor);
    public Date buscarPrazo(int idemprestimo);
    public void inserirValor(Double valor);
    public boolean livreDeMulta (int idLeitor);


}
