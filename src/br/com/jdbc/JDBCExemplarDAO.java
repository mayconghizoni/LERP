package br.com.jdbc;

import br.com.jdbcinterface.ExemplarDAO;
import br.com.modelo.Exemplar;
import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCExemplarDAO implements ExemplarDAO {

    private Connection conexao;

    public JDBCExemplarDAO (Connection conexao){
        this.conexao = conexao;
    }

    @Override
    public boolean inserir(Exemplar exemplar) {

        String comando = "INSERT INTO exemplares (idexemplares, titulo, autor, ano, edicao, idcategoria) VALUES (?,?,?,?,?,?)";

        PreparedStatement p;

        try{

            p = this.conexao.prepareStatement(comando);

            p.setInt(1, exemplar.getId());
            p.setString(2, exemplar.getTitulo());
            p.setString(3, exemplar.getAutor());
            p.setString(4, exemplar.getAno());
            p.setString(5, exemplar.getEdicao());
            p.setInt(6,exemplar.getCategoria());

            p.execute();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Exemplar> buscar(int statusParam) {

        String comando = "SELECT * FROM exemplares WHERE status =  "+statusParam+" ORDER BY titulo ASC;";

        List<Exemplar> listaExemplares = new ArrayList<Exemplar>();

        Exemplar exemplar = null;


        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while(rs.next()){
                exemplar = new Exemplar();

                exemplar.setId(rs.getInt("idexemplares"));
                exemplar.setTitulo(rs.getString("titulo"));
                exemplar.setAutor(rs.getString("autor"));
                exemplar.setAno(rs.getString("ano"));
                exemplar.setCategoria(rs.getInt("idcategoria"));
                exemplar.setEdicao(rs.getString("edicao"));
                exemplar.setStatus(rs.getInt("status"));

                listaExemplares.add(exemplar);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return listaExemplares;

    }

    @Override
    public boolean manutencao(int id) {

        String comando = "UPDATE exemplares SET status=? WHERE idexemplares=?";

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
}
