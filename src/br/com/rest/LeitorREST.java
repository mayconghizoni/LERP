package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCLeitorDAO;
import br.com.modelo.Leitor;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Path("leitor")
public class LeitorREST extends UtilRest {

    @POST
    @Path("inserir")
    @Consumes("application/*")
    public Response inserir(String leitorParam){

        try{

            Leitor leitor = new Gson().fromJson(leitorParam, Leitor.class);

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);

            boolean retorno = jdbcLeitor.inserir(leitor);

            if(retorno){
                return this.buildResponse("Leitor cadastrado com sucesso!");
            }else{
                return buildErrorResponse("Erro ao cadastrar leitor!");
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

            List<Leitor> listaLeitor = new ArrayList<Leitor>();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);
            listaLeitor = jdbcLeitor.buscar();

            return this.buildResponse(listaLeitor);

        }catch(Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }

    }

    @GET
    @Path("buscarPorId")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@QueryParam("id") int id){

        try{

            Leitor leitor = new Leitor();
            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);
            leitor = jdbcLeitor.buscarPorId(id);

            return this.buildResponse(leitor);

        }catch(Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }
    }

    @PUT
    @Path("alterar")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterar(String leitorParam){

        try{

            Leitor leitor = new Gson().fromJson(leitorParam, Leitor.class);
            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);

            boolean retorno = jdbcLeitor.alterar(leitor);

            if (retorno){
                return buildResponse("Leitor alterado com sucesso!");
            }else{
                return buildErrorResponse("Erro ao alterar leitor!");
            }

        }catch(Exception e){
            e.printStackTrace();
            return buildErrorResponse(e.getMessage());
        }

    }

    @PUT
    @Path("inativar/{id}")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response inativar(@PathParam("id") int id){

            try{

                Conexao conec = new Conexao();
                Connection conexao = conec.abrirConexao();
                JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);

                boolean retorno = jdbcLeitor.inativar(id);

                if(retorno){
                    return this.buildResponse("Leitor inativo!!!");
                }else {
                    return this.buildErrorResponse("Erro ao inativar leitor!");
                }

            }catch (Exception e){
                e.printStackTrace();
                return buildErrorResponse(e.getMessage());
            }

    }

    @PUT
    @Path("ativar/{id}")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ativar(@PathParam("id") int id){

        try{

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);

            boolean retorno = jdbcLeitor.ativar(id);

            if(retorno){
                return this.buildResponse("Leitor ativo!!!");
            }else {
                return this.buildErrorResponse("Erro ao ativar leitor!");
            }

        }catch (Exception e){
            e.printStackTrace();
            return buildErrorResponse(e.getMessage());
        }

    }

}
