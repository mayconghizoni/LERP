package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCUsuarioDAO;
import br.com.modelo.Usuario;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Path("usuario")
public class UsuarioREST extends UtilRest{

    @GET
    @Path("buscar")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(){

        try{

            List<Usuario> listaUsuario = new ArrayList<Usuario>();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
            listaUsuario = jdbcUsuario.buscar();

            return this.buildResponse(listaUsuario);

        }catch(Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }

    }



}
