package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCLeitorDAO;
import br.com.modelo.Leitor;
import com.google.gson.Gson;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.Connection;

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


}
