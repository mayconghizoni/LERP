package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCCategoriaDAO;
import br.com.modelo.Categoria;
import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.Connection;

@Path("categoria")
public class CategoriaREST extends UtilRest {

    @POST
    @Path("inserir")
    @Consumes("application/*")
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

}
