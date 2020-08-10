package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCUsuarioDAO;
import br.com.modelo.Usuario;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Path("usuario")
public class UsuarioREST extends UtilRest{

    @Context
    HttpServletRequest request;

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

    @GET
    @Path("buscarPorId")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@QueryParam("id") int id){

        try{

            Usuario usuario = new Usuario();
            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
            usuario = jdbcUsuario.buscarPorId(id);

            return this.buildResponse(usuario);

        }catch(Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }
    }

    @GET
    @Path("buscarLogado")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarLogado(){

        try{

            HttpSession session = ((HttpServletRequest) request).getSession();
            Object logado = session.getAttribute("login");

            int id = Integer.parseInt(logado.toString().replaceAll("\"",""));

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
            Usuario usuario = new Usuario();
            usuario = jdbcUsuario.buscarPorId(id);

            return this.buildResponse(usuario);

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

    @PUT
    @Path("alterar")
    @Consumes("application/*")
    public Response alterar(String usuarioParam){

        try{

            Usuario usuario = new Gson().fromJson(usuarioParam, Usuario.class);
            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);

            //INICIO CRIPTOGRAFIA
            if (!usuario.getSenha().equals("")) {
                usuario.setSenha(criptografar(usuario.getSenha()));
            }

            boolean retorno = jdbcUsuario.alterar(usuario);

            if (retorno){
                return buildResponse("Usuário alterado com sucesso!");
            }else{
                return buildErrorResponse("Erro ao alterar usuário!");
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
            JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);

            boolean retorno = jdbcUsuario.inativar(id);

            if(retorno){
                return this.buildResponse("Usuário inativo!!!");
            }else {
                return this.buildErrorResponse("Erro ao inativar usuário!");
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
            JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);

            boolean retorno = jdbcUsuario.ativar(id);

            if(retorno){
                return this.buildResponse("Usuário ativo!!!");
            }else {
                return this.buildErrorResponse("Erro ao ativar usuário!");
            }

        }catch (Exception e){
            e.printStackTrace();
            return buildErrorResponse(e.getMessage());
        }

    }

}
