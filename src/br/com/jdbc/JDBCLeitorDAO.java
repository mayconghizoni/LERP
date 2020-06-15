package br.com.jdbc;

import br.com.jdbcinterface.LeitorDAO;
import br.com.modelo.Leitor;

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
    public List<Leitor> buscar() {

        String comando = "SELECT * FROM leitor ORDER BY nome ASC;";

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

            p.setInt(1, 4);
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

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
