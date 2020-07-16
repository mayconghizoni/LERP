package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCUsuarioDAO;
import br.com.modelo.Usuario;
import com.google.gson.Gson;

import javax.ws.rs.*;
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

    @POST
    @Path("inserir")
    @Consumes("application/*")
    public Response inserir (String usuarioParam){

        try{

            Usuario usuario = new Gson().fromJson(usuarioParam, Usuario.class);
            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);

            //INICIO CRIPTOGRAFIA
            usuario.setSenha(criptografar(usuario.getSenha()));

            boolean retorno = jdbcUsuario.inserir(usuario);

            conec.fecharConexao();

            if(retorno){
                return this.buildResponse("Usuário cadastrado com sucesso!");
            }else {
                return this.buildResponse("Erro ao cadastrar usuário.");
            }

        }catch(Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }

    }

}
