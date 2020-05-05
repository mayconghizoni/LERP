package br.com.conexao.bd;

import java.sql.Connection;

public class Conexao {

    private Connection conexao;

    public Connection abriConexao(){
        try {

        }catch (Exception e){
            e.printStackTrace();
        }
        return conexao;
    }
}
