package br.com.jdbcinterface;

import br.com.modelo.Caixa;

public interface CaixaDAO {

    public Caixa valorCaixa();
    public boolean retirarValor(Caixa caixa);
    public boolean acrescentarValor(Caixa caixa);

}
