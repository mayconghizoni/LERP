package br.com.jdbc;

import br.com.jdbcinterface.LeitorDAO;
import br.com.modelo.Leitor;
import com.mysql.cj.xdevapi.Result;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCLeitorDAO implements LeitorDAO {

    private Connection conexao;

    public JDBCLeitorDAO (Connection conexao){
        this.conexao = conexao;
    }

    @Override
    public boolean inserir(Leitor leitor) {

        String comando = "INSERT INTO leitor (idleitor, nome, telefone, endereco, cpf, email) VALUES (?,?,?,?,?,?)";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);

            p.setInt(1,leitor.getId());
            p.setString(2, leitor.getNome());
            p.setString(3, leitor.getFone());
            p.setString(4, leitor.getEndereco());
            p.setString(5,leitor.getCpf());
            p.setString(6,leitor.getEmail());

            p.execute();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Leitor> buscar(int status) {

        String comando = "SELECT * FROM leitor WHERE status = "+status+" ORDER BY nome ASC;";

        List<Leitor> listaLeitores = new ArrayList<Leitor>();

        Leitor leitor = null;

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while(rs.next()){

                leitor = new Leitor();

                leitor.setId(rs.getInt("idleitor"));
                leitor.setCpf(rs.getString("cpf"));
                leitor.setNome(rs.getString("nome"));
                leitor.setFone(rs.getString("telefone"));
                leitor.setEndereco(rs.getString("endereco"));
                leitor.setStatus(rs.getInt("status"));
                leitor.setEmail(rs.getString("email"));
                leitor.setValorMulta(rs.getDouble("valorMulta"));
                leitor.setMulta(rs.getInt("multa"));

                listaLeitores.add(leitor);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return listaLeitores;
    }

    @Override
    public List<Leitor> buscarMultados(int multa) {

        String comando = "SELECT * FROM leitor WHERE multa = "+multa+" ORDER BY nome ASC;";

        List<Leitor> listaLeitores = new ArrayList<Leitor>();

        Leitor leitor = null;

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while(rs.next()){

                leitor = new Leitor();

                leitor.setId(rs.getInt("idleitor"));
                leitor.setCpf(rs.getString("cpf"));
                leitor.setNome(rs.getString("nome"));
                leitor.setFone(rs.getString("telefone"));
                leitor.setEndereco(rs.getString("endereco"));
                leitor.setStatus(rs.getInt("status"));
                leitor.setEmail(rs.getString("email"));
                leitor.setValorMulta(rs.getDouble("valorMulta"));
                leitor.setMulta(rs.getInt("multa"));

                listaLeitores.add(leitor);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return listaLeitores;
    }

    @Override
    public List<Leitor> buscarAtivos(int offset) {

        String comando = "SELECT * FROM leitor WHERE status = 1 ORDER BY nome ASC limit 9 offset "+offset+";";

        List<Leitor> listaLeitores = new ArrayList<Leitor>();

        Leitor leitor = null;

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while(rs.next()){

                leitor = new Leitor();

                leitor.setId(rs.getInt("idleitor"));
                leitor.setCpf(rs.getString("cpf"));
                leitor.setNome(rs.getString("nome"));
                leitor.setFone(rs.getString("telefone"));
                leitor.setEndereco(rs.getString("endereco"));
                leitor.setStatus(rs.getInt("status"));
                leitor.setEmail(rs.getString("email"));
                leitor.setValorMulta(rs.getDouble("valorMulta"));
                leitor.setMulta(rs.getInt("multa"));

                listaLeitores.add(leitor);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return listaLeitores;
    }

    @Override
    public List<Leitor> buscarInativos(int offset) {

        String comando = "SELECT * FROM leitor WHERE status = 3 ORDER BY nome ASC limit 9 offset "+offset+";";

        List<Leitor> listaLeitores = new ArrayList<Leitor>();

        Leitor leitor = null;

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while(rs.next()){

                leitor = new Leitor();

                leitor.setId(rs.getInt("idleitor"));
                leitor.setCpf(rs.getString("cpf"));
                leitor.setNome(rs.getString("nome"));
                leitor.setFone(rs.getString("telefone"));
                leitor.setEndereco(rs.getString("endereco"));
                leitor.setStatus(rs.getInt("status"));
                leitor.setEmail(rs.getString("email"));
                leitor.setValorMulta(rs.getDouble("valorMulta"));
                leitor.setMulta(rs.getInt("multa"));

                listaLeitores.add(leitor);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return listaLeitores;
    }

    @Override
    public List<Leitor> buscarComMultas(int offset) {

        String comando = "SELECT * FROM leitor WHERE multa = 1 ORDER BY nome ASC limit 9 offset "+offset+";";

        List<Leitor> listaLeitores = new ArrayList<Leitor>();

        Leitor leitor = null;

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while(rs.next()){

                leitor = new Leitor();

                leitor.setId(rs.getInt("idleitor"));
                leitor.setCpf(rs.getString("cpf"));
                leitor.setNome(rs.getString("nome"));
                leitor.setFone(rs.getString("telefone"));
                leitor.setEndereco(rs.getString("endereco"));
                leitor.setStatus(rs.getInt("status"));
                leitor.setEmail(rs.getString("email"));
                leitor.setValorMulta(rs.getDouble("valorMulta"));
                leitor.setMulta(rs.getInt("multa"));

                listaLeitores.add(leitor);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return listaLeitores;
    }

    @Override
    public Leitor buscarPorId(int id) {

        String comando = "SELECT * FROM leitor WHERE idleitor = ?";
        Leitor leitor = new Leitor();

        try{

            PreparedStatement p = this.conexao.prepareStatement(comando);
            p.setInt(1,id);
            ResultSet rs = p.executeQuery();

            if(rs.next()){

                leitor.setId(rs.getInt("idleitor"));
                leitor.setNome(rs.getString("nome"));
                leitor.setEmail(rs.getString("email"));
                leitor.setCpf(rs.getString("cpf"));
                leitor.setEndereco(rs.getString("endereco"));
                leitor.setStatus(rs.getInt("status"));
                leitor.setFone(rs.getString("telefone"));
                leitor.setValorMulta(rs.getDouble("valorMulta"));
                leitor.setMulta(rs.getInt("multa"));

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return leitor;
    }

    @Override
    public boolean alterar(Leitor leitor) {

        String comando = "UPDATE leitor SET nome=?, cpf=?, email=?, endereco=?, telefone=? WHERE idleitor=?";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);

            p.setString(1,leitor.getNome());
            p.setString(2,leitor.getCpf());
            p.setString(3,leitor.getEmail());
            p.setString(4,leitor.getEndereco());
            p.setString(5,leitor.getFone());
            p.setInt(6,leitor.getId());

            p.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean inativar(int id) {

        String comando = "UPDATE leitor SET status=? WHERE idleitor=?";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);

            p.setInt(1, 3);
            p.setInt(2, id);

            p.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    public boolean ativar(int id) {

        String comando = "UPDATE leitor SET status=? WHERE idleitor=?";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);

            p.setInt(1, 1);
            p.setInt(2, id);

            p.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public List<Leitor> buscarPorNome(String nome) {

        String comando = "SELECT * FROM leitor ";

        if(!nome.equals("")) {
            comando += "WHERE nome LIKE '%" + nome + "%' ";
        }

        comando += "ORDER BY nome ASC";

        List<Leitor> listaLeitores = new ArrayList<Leitor>();

        Leitor leitor = null;

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while(rs.next()){
                leitor = new Leitor();

                leitor.setId(rs.getInt("idleitor"));
                leitor.setNome(rs.getString("nome"));
                leitor.setFone(rs.getString("telefone"));
                leitor.setEndereco(rs.getString("endereco"));
                leitor.setCpf(rs.getString("cpf"));
                leitor.setEmail(rs.getString("email"));
                leitor.setStatus(rs.getInt("status"));
                leitor.setValorMulta(rs.getDouble("valorMulta"));
                leitor.setMulta((rs.getInt("multa")));

                listaLeitores.add(leitor);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return listaLeitores;

    }

    @Override
    public void adicionarMulta(double valor, int id) {

        String sql = "Select idleitor from leitor where idleitor in (select leitor_idleitor from emprestimos where idemprestimos = ?);";
        PreparedStatement ps;
        int idLeitor = 0;
        try{
            ps = this.conexao.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                idLeitor = rs.getInt("idleitor");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        JDBCEmprestimoDAO jdbcEmprestimoDAO = new JDBCEmprestimoDAO(conexao);
        boolean livre = jdbcEmprestimoDAO.livreDeMulta(idLeitor);
        double valorMulta = 0;

        if(!livre){
            String sql2 = "Select valorMulta from leitor where idLeitor = ?;";
            PreparedStatement ps2;
            try{
                ps2 = this.conexao.prepareStatement(sql2);
                ps2.setInt(1, idLeitor);
                ResultSet rs = ps2.executeQuery();
                if(rs.next()){
                    valorMulta = rs.getDouble("valorMulta");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(!livre){
            valor += valorMulta;
        }

        String comando = "UPDATE leitor SET valorMulta = ?, multa = 1 WHERE idleitor = ?;";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);
            p.setDouble(1,valor);
            p.setInt(2, idLeitor);
            p.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean pagarTotal(int id) {

        String comando = "UPDATE leitor SET multa = 0, valorMulta = 0 WHERE idleitor = ?;";

        PreparedStatement ps;

        try{

            ps = this.conexao.prepareStatement(comando);
            ps.setInt(1,id);
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void inserirValor(Double valor){

        double emCaixa = 0;

        String comando = "SELECT * From caixa;";

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            if (rs.next()){
                emCaixa = rs.getDouble("valor");
             }

        }catch (SQLException e){
            e.printStackTrace();
        }

        //Soma de valores
        double valorAtt = valor + emCaixa;

        String com = "update caixa set valor = ?";
        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(com);
            p.setDouble(1, valorAtt);
            p.execute();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean verificaValor(int id){

        String sql = "SELECT valorMulta FROM leitor WHERE idleitor = ?;";
        double valor = 0;
        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(sql);
            p.setInt(1,id);

            ResultSet rs = p.executeQuery();

            if (rs.next()){
                valor = rs.getDouble("valorMulta");
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        inserirValor(valor);
        return true;
    }

    @Override
    public double pegarDivida(int id) {

        double valor =0;

        String comando = "SELECT valorMulta FROM leitor WHERE idleitor = ?;";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);
            p.setInt(1,id);

            ResultSet rs = p.executeQuery();

            if (rs.next()){
                valor = rs.getDouble("valorMulta");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return valor;

    }

    @Override
    public boolean atualizarMulta(int id, double valor) {

        String comando = "UPDATE leitor SET valorMulta = ? WHERE idleitor = ?;";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);

            p.setDouble(1,valor);
            p.setInt(2,id);
            p.executeUpdate();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }
}
