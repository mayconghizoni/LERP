package br.com.rest;

import br.com.bd.Conexao;
import br.com.jdbc.JDBCUsuarioDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.util.Base64;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();

		JDBCUsuarioDAO objetoDAO = new JDBCUsuarioDAO(conexao);

		if (objetoDAO.validarLogin(request.getParameter("idUsuario"), criptografar(request.getParameter("password")))) {
			HttpSession sessao = request.getSession();
			sessao.setAttribute("login", request.getParameter("idUsuario"));
			response.sendRedirect("/LERP/pages/webapp/index.html");
		} else {
			response.sendRedirect("/LERP/");
		}

    }

    public static String criptografar(String senha){

        try{

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(senha.getBytes());
            byte[] codificado = md.digest();
            BigInteger hex = new BigInteger(1, codificado);
            String hashMD5 = hex.toString(16);
            while (hashMD5.length() < 32) {
                hashMD5 = 0 + hashMD5;
            }

            return hashMD5;

        }catch (Exception e){
            e.printStackTrace();
            return "Erro!";
        }

    }
}

