package br.com.modelo;

import java.io.Serializable;

public class Relatorio implements Serializable{

    private  static final long serialVersionUID = 1L;

    private int idRelatorio;
    private int idExemplar;
    private String tituloExemplar;
    private int idCategoria;
    private String dataSaida;
    private String dataDev;

    public int getIdRelatorio() {
        return idRelatorio;
    }

    public void setIdRelatorio(int idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    public int getIdExemplar() {
        return idExemplar;
    }

    public void setIdExemplar(int idExemplar) {
        this.idExemplar = idExemplar;
    }

    public String getTituloExemplar() {
        return tituloExemplar;
    }

    public void setTituloExemplar(String tituloExemplar) {
        this.tituloExemplar = tituloExemplar;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getDataDev() {
        return dataDev;
    }

    public void setDataDev(String dataDev) {
        this.dataDev = dataDev;
    }
}
