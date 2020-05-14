package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCExemplarDAO;
import br.com.modelo.Exemplar;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.Connection;

@Path("exemplar")
public class ExemplarREST extends UtilRest{

    @POST
    @Path("inserir")
    @Consumes("application/*")
    public Response inserir (String exemplarParam){

        try{
            Exemplar exemplar = new Gson().fromJson(exemplarParam, Exemplar.class);

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCExemplarDAO jdbcExemplar = new JDBCExemplarDAO(conexao);

            boolean retorno = jdbcExemplar.inserir(exemplar);

            if(retorno){
                return this.buildResponse("Exemplar cadastrado com sucesso!");
            }else{
                return buildErrorResponse("Erro ao cadastrar exemplar!");
            }

        }catch (Exception e){
            e.printStackTrace();
            return buildErrorResponse(e.getMessage());
        }

    }




}
