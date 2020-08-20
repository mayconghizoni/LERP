package br.com.modelo;

import java.util.GregorianCalendar;

public class Emprestimo {

    private  static final long serialVersionUID = 1L;

    private int id;
    private int idLivro;
    private int idLeitor;
    private String dataSaída;
    private double valorMulta;

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

    public String getDataSaída() {
        return dataSaída;
    }

    public void setDataSaída(String dataSaída) {
        this.dataSaída = dataSaída;
    }
}