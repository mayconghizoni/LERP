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
    @Path("buscarMultados/{multa}")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarMultados(@PathParam("multa") int multa){

        try{

            List<Leitor> listaLeitor = new ArrayList<Leitor>();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);
            listaLeitor = jdbcLeitor.buscarMultados(multa);

            return this.buildResponse(listaLeitor);

        }catch(Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }

    }

    @GET
    @Path("buscar/{status}")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(@PathParam("status") int status){

        try{

            List<Leitor> listaLeitor = new ArrayList<Leitor>();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);
            listaLeitor = jdbcLeitor.buscar(status);

            return this.buildResponse(listaLeitor);

        }catch(Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }

    }

    @GET
    @Path("buscarAtivos")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarAtivos(@QueryParam("offset") int offset){

        try{

            List<Leitor> listaLeitor = new ArrayList<Leitor>();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);
            listaLeitor = jdbcLeitor.buscarAtivos(offset);

            return this.buildResponse(listaLeitor);

        }catch(Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }

    }

    @GET
    @Path("buscarInativos")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarInativos(@QueryParam("offset")int offset){

        try{

            List<Leitor> listaLeitor = new ArrayList<Leitor>();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);
            listaLeitor = jdbcLeitor.buscarInativos(offset);

            return this.buildResponse(listaLeitor);

        }catch(Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }

    }

    @GET
    @Path("buscarComMultas")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarComMultas(@QueryParam("offset") int offset){

        try{

            List<Leitor> listaLeitor = new ArrayList<Leitor>();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);
            listaLeitor = jdbcLeitor.buscarComMultas(offset);

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

    @GET
    @Path("/buscarPorNome")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorNome(@QueryParam("valorBusca") String valorBusca){

        try {

            List<Leitor> listaLeitores = new ArrayList<Leitor>();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);
            listaLeitores = jdbcLeitor.buscarPorNome(valorBusca);

            conec.fecharConexao();

            return this.buildResponse(listaLeitores);

        }catch(Exception e){
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }
    }

    @PUT
    @Path("pagarTotal/{id}")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pagarTotal(@PathParam("id") int id){

        try {
            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);
            boolean adicionado = jdbcLeitor.verificaValor(id);

            if(adicionado){
                boolean retorno = jdbcLeitor.pagarTotal(id);
                conec.fecharConexao();

                if (retorno){
                    return buildResponse("Multa paga com sucesso!");
                }else{
                    return buildErrorResponse("Erro ao acertar multa, tente novamente!");
                }
            }else{
                return buildErrorResponse("Erro ao acertar multa, tente novamente!");
            }

        }catch (Exception e){
            return buildErrorResponse(e.getMessage());
        }

    }

    @PUT
    @Path("pagarParcial")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pagarParcial(String leitorParam) {

        try {

            Leitor leitor = new Gson().fromJson(leitorParam, Leitor.class);
            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);

            double valorDivida = jdbcLeitor.pegarDivida(leitor.getId());
            double novaDivida = valorDivida - leitor.getValorMulta();
            boolean multaAtualizada = jdbcLeitor.atualizarMulta(leitor.getId(),novaDivida);

            if (multaAtualizada){
                jdbcLeitor.inserirValor(leitor.getValorMulta());
                return buildResponse("Multa paga parcialmente! Sua nova divida é dê: "+novaDivida);
            }else{
                return buildErrorResponse("Erro ao calcular multa!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return buildErrorResponse(e.getMessage());
        }

    }

}
