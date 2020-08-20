package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCEmprestimoDAO;
import br.com.modelo.Emprestimo;
import com.google.gson.Gson;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Path("emprestimo")
public class EmprestimosREST extends UtilRest{

    @POST
    @Path("inserir")
    @Consumes("application/*")
    public Response inserir(String emprestimoParam){

        Emprestimo emprestimo = new Gson().fromJson(emprestimoParam, Emprestimo.class);
        Conexao conec = new Conexao();
        Connection conexao = conec.abrirConexao();

        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
        emprestimo.setDataSaída(formatarDate.format(data));

        JDBCEmprestimoDAO jdbcEmprestimo = new JDBCEmprestimoDAO(conexao);

        boolean existenciaLeitor = jdbcEmprestimo.verificaExistenciaLeitor(emprestimo.getIdLeitor());
        boolean existenciaLivro = jdbcEmprestimo.verificaExistenciaLivro(emprestimo.getIdLivro());

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

    }

}
