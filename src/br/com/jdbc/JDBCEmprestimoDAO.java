package br.com.jdbc;

import br.com.jdbcinterface.EmprestimoDAO;
import br.com.modelo.Emprestimo;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JDBCEmprestimoDAO implements EmprestimoDAO {
    private Connection conexao;

    public JDBCEmprestimoDAO (Connection conexao){
        this.conexao = conexao;
    }

    @Override
    public boolean inserir(Emprestimo emprestimo) {

        String comando = "INSERT INTO emprestimos (leitor_idleitor, exemplar_idexemplar, dataSaida, dataDev) VALUES (? , ? , ?, ?);";

        PreparedStatement p;

        try {

            p = this.conexao.prepareStatement(comando);

            p.setInt(1, emprestimo.getIdLeitor());
            p.setInt(2, emprestimo.getIdLivro());
            p.setString(3, emprestimo.getDataSaida());
            p.setString(4, emprestimo.getDataDev());

            p.execute();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean verificaExistenciaLeitor(int id) {

        String comando = "SELECT * from leitor WHERE status = 1 AND idleitor = "+id+";";

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            if(rs.next()){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return false;
    }

    @Override
    public boolean verificaExistenciaLivro(int id) {

        String comando = "SELECT * from exemplares WHERE idexemplares = "+id+" AND status = 1;";

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            if(rs.next()){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return false;
    }

    @Override
    public List<Emprestimo> buscar() {

        String comando = "SELECT emp.*, l.nome, ex.titulo  FROM emprestimos as emp INNER JOIN leitor as l on leitor_idleitor = idleitor inner join exemplares as ex on exemplar_idexemplar = idexemplares ORDER BY dataSaida ASC;";

        List<Emprestimo> listaEmprestimos = new ArrayList<Emprestimo>();

        Emprestimo emprestimo = null;

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while (rs.next()){

                emprestimo = new Emprestimo();

                emprestimo.setId(rs.getInt("idemprestimos"));
                emprestimo.setIdLivro(rs.getInt("exemplar_idexemplar"));
                emprestimo.setIdLeitor(rs.getInt("leitor_idleitor"));
                emprestimo.setDataSaida(rs.getString("dataSaida"));
                emprestimo.setDataDev(rs.getString("datadev"));
                emprestimo.setNomeLeitor(rs.getString("nome"));
                emprestimo.setTituloExemplar(rs.getString("titulo"));

                listaEmprestimos.add(emprestimo);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return listaEmprestimos;

    }

    @Override
    public boolean finalizar(int id) {
        String comando = "DELETE FROM emprestimos WHERE idemprestimos = ?;";
        PreparedStatement p;

        try {

            p = this.conexao.prepareStatement(comando);
            p.setInt(1, id);
            p.execute();

        }catch(SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    public boolean maisSete(Emprestimo emprestimo) {

        String comando = "UPDATE emprestimos SET dataDev = ? WHERE idemprestimos = ?;";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);
            p.setString(1, emprestimo.getDataDev());
            p.setInt(2, emprestimo.getId());

            p.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean verificaQuantidade(int idLeitor) {

        String comando = "SELECT COUNT(*) AS quantidade from emprestimos WHERE leitor_idleitor = ?;";

        PreparedStatement p;
        int quantidade = 0;

        try{

            p = this.conexao.prepareStatement(comando);
            p.setInt(1, idLeitor);

            ResultSet rs = p.executeQuery();

            while (rs.next()){
                quantidade = rs.getInt("quantidade");
            }

            if(quantidade > 2){
                return false;
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    public Date buscarPrazo(int idemprestimo) {

        String com = "SELECT dataDev FROM emprestimos WHERE idemprestimos = ?;";

        PreparedStatement p;

        String dataRet = "";

        try{

            p = this.conexao.prepareStatement(com);
            p.setInt(1, idemprestimo);

            ResultSet rs = p.executeQuery();

            if (rs.next()){
                dataRet = rs.getString("dataDev");
            }

            Date dataDev = new SimpleDateFormat("yyyy-MM-dd").parse(dataRet);

            return dataDev;

        }catch (SQLException | ParseException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean livreDeMulta(int idLeitor) {

        String comando = "SELECT multa FROM leitor WHERE idleitor = ?;";

        PreparedStatement p;
        int multa = 0;

        try{

            p = this.conexao.prepareStatement(comando);
            p.setInt(1, idLeitor);
            ResultSet rs = p.executeQuery();

            if(rs.next()){
                multa = rs.getInt("multa");
            }
            if (multa == 0 ){
                return true;
            }else{
                return false;
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Emprestimo> buscarPorParametro(String valorBusca) {

        String comando = "SELECT * FROM emprestimos as emp INNER JOIN leitor as l on leitor_idleitor = idleitor inner join exemplares as ex on exemplar_idexemplar = idexemplares ";

        if(!valorBusca.equals("")){
            comando+= "WHERE l.nome LIKE '%"+valorBusca+"%' OR ex.titulo LIKE '%"+valorBusca+"%' OR ex.autor LIKE '%"+valorBusca+"%' ";
        }

        comando += "ORDER BY emp.dataDev ASC;";

        List<Emprestimo> listaEmprestimos = new ArrayList<Emprestimo>();

        Emprestimo emprestimo = null;

        try{
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while (rs.next()){
                emprestimo = new Emprestimo();

                emprestimo.setId(rs.getInt("idemprestimos"));
                emprestimo.setIdLivro(rs.getInt("exemplar_idexemplar"));
                emprestimo.setIdLeitor(rs.getInt("leitor_idleitor"));
                emprestimo.setDataSaida(rs.getString("dataSaida"));
                emprestimo.setDataDev(rs.getString("datadev"));
                emprestimo.setNomeLeitor(rs.getString("nome"));
                emprestimo.setTituloExemplar(rs.getString("titulo"));

                listaEmprestimos.add(emprestimo);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return listaEmprestimos;
    }
}
