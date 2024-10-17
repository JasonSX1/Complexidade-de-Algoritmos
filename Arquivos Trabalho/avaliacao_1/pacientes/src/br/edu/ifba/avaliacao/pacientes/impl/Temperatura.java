package br.edu.ifba.avaliacao.pacientes.impl;

public class Temperatura implements Comparable<Temperatura> {

    Integer valor = 0;

    public Integer getValor() {
        return valor;
    }


    public void setValor(Integer valor) {
        this.valor = valor;
    }


    public Temperatura(Integer temperatura) {
        this.valor = temperatura;
    }
    
    @Override
    public String toString() {
        return "temperatura: " + valor;
    }

    @Override
    public int compareTo(Temperatura o) {
        return Integer.valueOf(valor).compareTo(o.getValor());
    }
    
}
