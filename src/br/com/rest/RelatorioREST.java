package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCRelatorioDAO;
import br.com.modelo.Grafico;
import br.com.modelo.Relatorio;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Path("relatorio")
public class RelatorioREST extends UtilRest{

    @GET
    @Path("buscar")
    @Consumes("application/*")
    public Response buscar (@QueryParam("dataInicio") String dataInicio , @QueryParam("dataFim") String dataFim){

        try{

            List<Relatorio> listaRelatorio  = new ArrayList<Relatorio>();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCRelatorioDAO jdbcRelatorio = new JDBCRelatorioDAO(conexao);
            listaRelatorio = jdbcRelatorio.buscar(dataInicio, dataFim);

            return this.buildResponse(listaRelatorio);

        }catch (Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }

    }
    @GET
    @Path("buscarGrafico")
    @Consumes("application/*")
    public Response buscarGrafico (@QueryParam("dataInicio") String dataInicio , @QueryParam("dataFim") String dataFim){

        try{

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCRelatorioDAO jdbcRelatorio = new JDBCRelatorioDAO(conexao);
            Grafico grafico = jdbcRelatorio.buscarGrafico(dataInicio, dataFim);

            return this.buildResponse(grafico);

        }catch (Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }

    }

}
