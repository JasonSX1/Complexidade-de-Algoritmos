package edu.ifba.avaliacao.cassino.impl;

public class Roleta {

    private int numeroSorteado;
    private String corSorteada;
    private String tipoSorteado; // Ímpar/Par

    public Roleta(int numeroSorteado, String corSorteada, String tipoSorteado) {
        this.numeroSorteado = numeroSorteado;
        this.corSorteada = corSorteada;
        this.tipoSorteado = tipoSorteado;
    }

    public int getNumeroSorteado() { return numeroSorteado; }
    public String getCorSorteada() { return corSorteada; }
    public String getTipoSorteado() { return tipoSorteado; }
    
}
