package br.com.jdbcinterface;

import br.com.modelo.Emprestimo;

public interface EmprestimoDAO {

    public boolean inserir(Emprestimo emprestimo);
    public boolean verificaExistenciaLeitor(int id);
    public boolean verificaExistenciaLivro(int id);

}
