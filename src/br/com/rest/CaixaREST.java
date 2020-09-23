package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCCaixaDAO;
import br.com.modelo.Caixa;
import com.google.gson.Gson;

import javax.ws.rs.*;
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
                conec.fecharConexao();
                return buildResponse(caixa);
            }else{
                conec.fecharConexao();
                return buildErrorResponse("Erro ao buscar valor!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return buildErrorResponse(e.getMessage());
        }
    }

    @PUT
    @Path("retirarValor")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retirarValor(String caixaParam){

        try{

            Caixa caixa = new Gson().fromJson(caixaParam, Caixa.class);
            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCCaixaDAO jdbcCaixa = new JDBCCaixaDAO(conexao);
            boolean retorno = jdbcCaixa.retirarValor(caixa);

            if (retorno){
                conec.fecharConexao();
                return buildResponse("Valor retirado de caixa!");
            }else{
                conec.fecharConexao();
                return buildErrorResponse("Erro ao retirar valor de caixa;");
            }

        }catch (Exception e){
            e.printStackTrace();
            return buildErrorResponse(e.getMessage());
        }
    }

    @PUT
    @Path("acrescentarValor")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response acrescentarValor(String caixaParam){

        try{

            Caixa caixa = new Gson().fromJson(caixaParam, Caixa.class);
            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCCaixaDAO jdbcCaixa = new JDBCCaixaDAO(conexao);
            boolean retorno = jdbcCaixa.acrescentarValor(caixa);

            if (retorno){
                conec.fecharConexao();
                return buildResponse("Valor acrescentado em caixa!");
            }else{
                conec.fecharConexao();
                return buildErrorResponse("Erro ao acrescentar valor em caixa;");
            }

        }catch (Exception e){
            e.printStackTrace();
            return buildErrorResponse(e.getMessage());
        }
    }

}
