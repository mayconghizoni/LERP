package br.com.bd;

import java.sql.Connection;

public class Conexao {

    private Connection conexao;

    public Connection abrirConexao(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//Seleciona driver JDBC
            conexao = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/bd_LERP?" +
                    "user=maycon&password=root&useTimezone=true&serverTimezone=UTC"); //Passas os parametros da conexão atual
        }catch (Exception e){
            e.printStackTrace();
        }
        return conexao;
    }

    public void fecharConexao() {

        try {
            conexao.close();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }
}
