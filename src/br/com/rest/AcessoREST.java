package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCAcessoDAO;
import br.com.modelo.Acesso;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Path("acesso")
public class AcessoREST extends UtilRest {

    @GET
    @Path("buscar")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar (){

        try{
            List<Acesso> listaAcesso = new ArrayList<Acesso>();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();

            JDBCAcessoDAO jdbcAcesso = new JDBCAcessoDAO(conexao);
            listaAcesso = jdbcAcesso.buscar();

            conec.fecharConexao();

            return this.buildResponse(listaAcesso);

        }catch(Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }
    }

}
