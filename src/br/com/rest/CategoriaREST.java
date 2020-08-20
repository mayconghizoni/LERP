package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCCategoriaDAO;
import br.com.modelo.Categoria;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Path("categoria")
public class CategoriaREST extends UtilRest {

    @POST
    @Path("inserir")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserir (String categoriaParam){

        try{
            Categoria categoria = new Gson().fromJson(categoriaParam, Categoria.class);
            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);

            boolean retorno = jdbcCategoria.inserir(categoria);

            conec.fecharConexao();

            if (retorno) {
                return this.buildResponse("Categoria cadastrada com sucesso!");
            }else {
                return this.buildErrorResponse("Erro ao cadastrar categoria!");
            }

        }catch (Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }

    }

    @GET
    @Path("buscar")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar (){

        try{
            List<Categoria> listaCategoria = new ArrayList<Categoria>();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
            listaCategoria = jdbcCategoria.buscar();

            conec.fecharConexao();

            return this.buildResponse(listaCategoria);


        }catch(Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }
    }


}
