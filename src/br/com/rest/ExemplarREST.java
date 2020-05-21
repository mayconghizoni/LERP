package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCExemplarDAO;
import br.com.modelo.Exemplar;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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

    @GET
    @Path("buscar")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(){

        try{

            List<Exemplar> listaExemplares = new ArrayList<Exemplar>();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCExemplarDAO jdbcExemplar = new JDBCExemplarDAO(conexao);
            listaExemplares = jdbcExemplar.buscar();

            return this.buildResponse(listaExemplares);

        }catch(Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }
    }




}
