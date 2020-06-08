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
}
