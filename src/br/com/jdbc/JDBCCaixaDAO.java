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

    @Override
    public boolean retirarValor(Caixa caixa) {

        String comando = "SELECT valor FROM caixa;";
        double valorAtual = 0;

        try {

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            if (rs.next()){
                valorAtual = rs.getDouble("valor");
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        double novoValor = valorAtual - caixa.getValor();

        String sql = "UPDATE caixa SET valor = ?;";

        PreparedStatement p ;

        try{

            p = this.conexao.prepareStatement(sql);
            p.setDouble(1,novoValor);
            p.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            return  false;
        }

        String sql2= "INSERT INTO movimentacao(valor,idMotivo) VALUES (?,?);";

        PreparedStatement p2;

        try{

            p = this.conexao.prepareStatement(sql2);
            p.setDouble(1,caixa.getValor());
            p.setInt(2,caixa.getMotivo());
            p.execute();

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    public boolean acrescentarValor(Caixa caixa) {
        String comando = "SELECT valor FROM caixa;";
        double valorAtual = 0;

        try {

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            if (rs.next()){
                valorAtual = rs.getDouble("valor");
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        double novoValor = valorAtual + caixa.getValor();

        String sql = "UPDATE caixa SET valor = ?;";

        PreparedStatement p ;

        try{

            p = this.conexao.prepareStatement(sql);
            p.setDouble(1,novoValor);
            p.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            return  false;
        }

        String sql2= "INSERT INTO movimentacao(valor,idMotivo) VALUES (?,?);";

        PreparedStatement p2;

        try{

            p = this.conexao.prepareStatement(sql2);
            p.setDouble(1,caixa.getValor());
            p.setInt(2,caixa.getMotivo());
            p.execute();

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
