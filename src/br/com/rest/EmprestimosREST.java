package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCEmprestimoDAO;
import br.com.jdbc.JDBCLeitorDAO;
import br.com.modelo.Emprestimo;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Path("emprestimo")
public class EmprestimosREST extends UtilRest{

    @POST
    @Path("inserir")
    @Consumes("application/*")
    public Response inserir(String emprestimoParam){

        Emprestimo emprestimo = new Gson().fromJson(emprestimoParam, Emprestimo.class);
        Conexao conec = new Conexao();
        Connection conexao = conec.abrirConexao();

        Date dataSaida = new Date(System.currentTimeMillis());
        SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
        emprestimo.setDataSaida(formatarDate.format(dataSaida));

        Date dataDev = new Date();
        dataDev = adicionarSete(dataSaida);
        emprestimo.setDataDev(formatarDate.format(dataDev));

        JDBCEmprestimoDAO jdbcEmprestimo = new JDBCEmprestimoDAO(conexao);

        boolean existenciaLeitor = jdbcEmprestimo.verificaExistenciaLeitor(emprestimo.getIdLeitor());
        boolean existenciaLivro = jdbcEmprestimo.verificaExistenciaLivro(emprestimo.getIdLivro());
        boolean emprestouTres = jdbcEmprestimo.verificaQuantidade(emprestimo.getIdLeitor());
        boolean multaPendente = jdbcEmprestimo.livreDeMulta(emprestimo.getIdLeitor());

        if(multaPendente){
            if (emprestouTres){
                if (existenciaLivro){
                    if (existenciaLeitor){
                        boolean retorno = jdbcEmprestimo.inserir(emprestimo);

                        if (retorno){
                            conec.fecharConexao();
                            return this.buildResponse("Empréstimo adicionado com sucesso!");
                        }else{
                            conec.fecharConexao();
                            return this.buildErrorResponse("Erro ao cadastrar empréstimo!");
                        }
                    }else{
                        conec.fecharConexao();
                        return this.buildErrorResponse("Usuário inexistênte!");
                    }
                }else{
                    conec.fecharConexao();
                    return this.buildErrorResponse("Livro inexistênte!");
                }
            }else{
                conec.fecharConexao();
                return this.buildErrorResponse("Limite de empréstimos atingidos!");
            }
        }else{
            conec.fecharConexao();
            return this.buildErrorResponse("Este leitor tem multas pendentes!");
        }
    }

    @GET
    @Path("buscar")
    @Consumes("application/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(){

        try{

            List<Emprestimo> listaEmprestimos = new ArrayList<Emprestimo>();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCEmprestimoDAO jdbcEmprestimo = new JDBCEmprestimoDAO(conexao);
            listaEmprestimos = jdbcEmprestimo.buscar();

            return this.buildResponse(listaEmprestimos);

        }catch (Exception e){
            e.printStackTrace();
            return buildErrorResponse(e.getMessage());
        }

    }

    @DELETE
    @Path("finalizar/{id}")
    @Consumes("application/*")
    public Response finalizar (@PathParam("id") int id){

        try{
            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCEmprestimoDAO jdbcEmprestimo = new JDBCEmprestimoDAO(conexao);

            Date dataDev = jdbcEmprestimo.buscarPrazo(id);
            int dias = contaDias(dataDev);

            boolean hasMulta = false;
            String stringMulta = " e não há multas.";

            if(dias < 0){
                hasMulta = true;
                dias = dias * -1;
                Double valor = 1.50 * dias;
                jdbcEmprestimo.inserirValor(valor);
                stringMulta = "com multa no valor de R$"+ valor;
                JDBCLeitorDAO jdbcLeitor = new JDBCLeitorDAO(conexao);
                jdbcLeitor.adicionarMulta(valor, id);
            }

            boolean retorno = jdbcEmprestimo.finalizar(id);

            conec.fecharConexao();
            if(retorno){
                return buildResponse("Empréstimo finalizado com sucesso, "+stringMulta);
            }else{
                return buildErrorResponse("Erro ao finalizar empréstimo.");
            }

        }catch (Exception e){
            e.printStackTrace();
            return buildErrorResponse(e.getMessage());
        }

    }

    @PUT
    @Path("maisSete/{id}")
    @Consumes("application/*")
    public Response maisSeteDias (@PathParam("id") int id){

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(id);
        Conexao conec = new Conexao();
        Connection conexao = conec.abrirConexao();

        Date dataAtual = new Date(System.currentTimeMillis());
        SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
        Date dataDev = new Date();
        dataDev = adicionarSete(dataAtual);
        emprestimo.setDataDev(formatarDate.format(dataDev));

        JDBCEmprestimoDAO jdbcEmprestimo = new JDBCEmprestimoDAO(conexao);

        boolean retorno = jdbcEmprestimo.maisSete(emprestimo);

        if(retorno){
            return buildResponse("Prazo prorrogado!");
        }else{
            return buildErrorResponse("Erro ao estender prazo de empréstimo.");
        }

    }


}
