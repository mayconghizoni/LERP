package br.com.jdbc;

import br.com.jdbcinterface.RelatorioDAO;
import br.com.modelo.Relatorio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCRelatorioDAO implements RelatorioDAO {

    private Connection conexao;

    public JDBCRelatorioDAO (Connection conexao){
        this.conexao = conexao;
    }

    @Override
    public List<Relatorio> buscar(String dataInicio, String dataFim) {

        String comando = "select * FROM historico_emprestimos WHERE dataSaida between '"+dataInicio+"' AND '"+dataFim+"';";

        List<Relatorio> listaRelatorio = new ArrayList<Relatorio>();
        Relatorio relatorio = null;

        System.out.println(comando);

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while (rs.next()){

                relatorio = new Relatorio();

                relatorio.setIdRelatorio(rs.getInt("idhistorico"));
                relatorio.setIdExemplar(rs.getInt("idExemplar"));
                relatorio.setTituloExemplar(rs.getString("tituloExemplar"));
                relatorio.setIdCategoria(rs.getInt("idCategoria"));
                relatorio.setDataSaida(rs.getString("dataSaida"));
                relatorio.setDataDev(rs.getString("dataDev"));

                listaRelatorio.add(relatorio);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return listaRelatorio;
    }

}
