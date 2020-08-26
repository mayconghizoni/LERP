package br.com.modelo;

import java.util.GregorianCalendar;

public class Emprestimo {

    private  static final long serialVersionUID = 1L;

    private int id;
    private int idLivro;
    private int idLeitor;
    private String dataSaida;
    private double valorMulta;
    private String nomeLeitor;
    private String tituloExemplar;

    public String getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getNomeLeitor() {
        return nomeLeitor;
    }

    public void setNomeLeitor(String nomeLeitor) {
        this.nomeLeitor = nomeLeitor;
    }

    public String getTituloExemplar() {
        return tituloExemplar;
    }

    public void setTituloExemplar(String tituloExemplar) {
        this.tituloExemplar = tituloExemplar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public int getIdLeitor() {
        return idLeitor;
    }

    public void setIdLeitor(int idLeitor) {
        this.idLeitor = idLeitor;
    }

    public double getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(double valorMulta) {
        this.valorMulta = valorMulta;
    }

}