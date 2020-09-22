package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCCaixaDAO;
import br.com.modelo.Caixa;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;

@Path("caixa")
public class CaixaREST extends UtilRest{

    @GET
    @Path("buscarValor")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarValor(){
        try{
            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCCaixaDAO jdbcCaixa = new JDBCCaixaDAO(conexao);
            Caixa caixa = jdbcCaixa.valorCaixa();
            if (caixa != null){
                return buildResponse(caixa);
            }else{
                return buildErrorResponse("Erro ao buscar valor!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return buildErrorResponse(e.getMessage());
        }
    }

}
