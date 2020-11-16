package br.com.modelo;

import java.util.List;

public class Grafico {

    private List<String> categorias;
    private List<Integer> quantidades;

    public List<String> getCategoria() {
        return categorias;
    }

    public void setCategoria(List<String> categorias) {
        this.categorias = categorias;
    }

    public List<Integer> getQuantidade() {
        return quantidades;
    }

    public void setQuantidade(List<Integer> quantidades) {
        this.quantidades = quantidades;
    }
}
