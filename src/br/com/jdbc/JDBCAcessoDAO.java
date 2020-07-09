package br.com.jdbc;

import br.com.jdbcinterface.AcessoDAO;
import br.com.modelo.Acesso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCAcessoDAO implements AcessoDAO {

    private Connection conexao;

    public JDBCAcessoDAO (Connection conexao){
        this.conexao = conexao;
    }

    @Override
    public List<Acesso> buscar() {

        String comando = "SELECT * FROM acesso ORDER BY descricao ASC;";

        List<Acesso> listaAcesso = new ArrayList<Acesso>();

        Acesso acesso = null;

        try{

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(comando);

            while(rs.next()){
                acesso = new Acesso();
                acesso.setId(rs.getInt("idacesso"));
                acesso.setDescricao(rs.getString("descricao"));

                listaAcesso.add(acesso);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return listaAcesso;

    }
}
