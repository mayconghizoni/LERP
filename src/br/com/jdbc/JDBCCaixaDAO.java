package br.com.jdbc;

import br.com.jdbcinterface.CaixaDAO;
import br.com.modelo.Caixa;

import java.sql.*;

public class JDBCCaixaDAO implements CaixaDAO {

    private Connection conexao;

    public JDBCCaixaDAO (Connection conexao){
        this.conexao = conexao;
    }

    @Override
    public Caixa valorCaixa() {

        Caixa caixa = null;

        String comando = "SELECT valor FROM caixa;";

        try{

        Statement stmt = conexao.createStatement();
        ResultSet rs = stmt.executeQuery(comando);

        if(rs.next()){
            caixa = new Caixa();
            caixa.setValor(rs.getDouble("valor"));
        }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return caixa;

    }
}
