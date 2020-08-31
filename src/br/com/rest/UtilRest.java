package br.com.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.google.gson.Gson;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Base64;

public class UtilRest {
	
	/*  Abaxio o metodo responsavel por enviar a resposta ao cliente sobre a transação realizada 
	 * (inclusão consulta edicao ou exclusao) caso ela seja realizada com sucesso.
	 * Repare que o método em questão aguarda que seja repassado um conteúdo que será referênciado por
	 * um objeto chamado result*/
	
	public Response buildResponse(Object result) {
		try {
			
			/*
			 * retorna o objeto de resposta com status 200 (ok), tendo em seu corpo
			 * valorResposta (que consiste no objeto convertido para JSON).
			 */
			
			String valorResposta = new Gson().toJson(result);
			return Response.ok(valorResposta).build();
		}catch(Exception ex) {
			ex.printStackTrace();
			//Se algo der errado acima, cria Response de erro
			return this.buildErrorResponse(ex.getMessage());
		}
	}
	
	/* Abaxio o metodo responsavel por enviar a resposta ao cliente sobre a transação realizada 
	 * (inclusão consulta edicao ou exclusao) caso ela não seja realizada com sucesso.
	 * Obseve que o método em questão aguarda que seja repassado um conteúdo que será referênciado por um
	 * objeto chamado rb.
	 */
	
	public Response buildErrorResponse(String str) {
		
		/*
		 * Abaixo o objeto rb recebe o status do erro.
		 */
		
		ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
		
		/*
		 * Define a entidade (objeto), que nesse caso é uma mensagem que será retornado para o cliente.
		 */
		
		rb = rb.entity(str);
		
		/*
		 * Define o tipo de retorno desta entidade(objeto), no caso é definido como texto simples;
		 */
		
		rb = rb.type("text/plain");
		
		/*
		 * Retorna um objeto de resposta com status 500 (erro) junto com a String contendo a mensagem de erro.
		 */
		
		return rb.build();
		
	}

	public static String criptografar(String senha){

		try{

			byte[] decodedBytes = Base64.getDecoder().decode(senha);
			String decodedString = new String(decodedBytes);

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(decodedString.getBytes());
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

	public static Date adicionarSete(Date data){

		int dia = data.getDate();
		int mes = data.getMonth();
		int ano = data.getYear();
		int ultimoDia = 0;

		if(mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12){
			ultimoDia = 31;
		}else if(mes == 2){
			if((ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0)){
				ultimoDia = 29;
			}else{
				ultimoDia = 28;
			}
		}else if(mes == 4 || mes == 6 || mes == 9 || mes == 11){
			ultimoDia = 30;
		}

		if(dia + 7 > ultimoDia){

			if (mes == 12){
				mes = 1;
				ano++;
			}else{
				mes++;
			}

			if (ultimoDia == 31){
				if (dia == 25){
					dia = 1;
				}
				if (dia == 26){
					dia = 2;
				}
				if (dia == 27){
					dia = 3;
				}
				if (dia == 28){
					dia = 4;
				}
				if (dia == 29){
					dia = 5;
				}
				if (dia == 30){
					dia = 6;
				}
				if (dia == 31){
					dia = 7;
				}
			}else if(ultimoDia == 30){
				if (dia == 24){
					dia = 1;
				}
				if (dia == 25){
					dia = 2;
				}
				if (dia == 26){
					dia = 3;
				}
				if (dia == 27){
					dia = 4;
				}
				if (dia == 28){
					dia = 5;
				}
				if (dia == 29){
					dia = 6;
				}
				if (dia == 30){
					dia = 7;
				}
			}else if(ultimoDia == 29){
				if (dia == 23){
					dia = 1;
				}
				if (dia == 24){
					dia = 2;
				}
				if (dia == 25){
					dia = 3;
				}
				if (dia == 26){
					dia = 4;
				}
				if (dia == 27){
					dia = 5;
				}
				if (dia == 28){
					dia = 6;
				}
				if (dia == 29){
					dia = 7;
				}
			}else if(ultimoDia == 28){
				if (dia == 22){
					dia = 1;
				}
				if (dia == 23){
					dia = 2;
				}
				if (dia == 24){
					dia = 3;
				}
				if (dia == 25){
					dia = 4;
				}
				if (dia == 26){
					dia = 5;
				}
				if (dia == 27){
					dia = 6;
				}
				if (dia == 28){
					dia = 7;
				}
			}
		}else{
			dia = dia + 7;
		}

		Date dataDev = new Date();

		dataDev.setDate(dia);
		dataDev.setMonth(mes);
		dataDev.setYear(ano);

		return dataDev;

	}

}
