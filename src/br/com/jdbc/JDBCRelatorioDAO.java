package br.com.jdbc;

import br.com.jdbcinterface.RelatorioDAO;
import br.com.modelo.Grafico;
import br.com.modelo.Relatorio;

import javax.persistence.criteria.CriteriaBuilder;
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

    public Grafico buscarGrafico (String dataInicio, String dataFim){

        String comando = "select count(he.idCategoria) as quantidade, c.descricao from historico_emprestimos he " +
                         "inner join categoria c on c.idCategoria = he.idCategoria " +
                         "where date(he.dataSaida) between '" + dataInicio + "' and '" + dataFim + "' " +
                         "group by he.idCategoria;";

        List<Integer> quantidades = new ArrayList<Integer>();
        List<String> categorias = new ArrayList<String>();
        Grafico grafico = new Grafico();
        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while (rs.next()){

                quantidades.add(rs.getInt("quantidade"));
                categorias.add(rs.getString("descricao"));

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        grafico.setCategoria(categorias);
        grafico.setQuantidade(quantidades);
        return grafico;
    }

}
