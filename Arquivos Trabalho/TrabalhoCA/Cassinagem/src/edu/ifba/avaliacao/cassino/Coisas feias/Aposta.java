package edu.ifba.avaliacao.cassino.impl;

public class Aposta {
    private int numeroApostado;
    private String corApostada;
    private String tipoApostado; // Ímpar/Par
    private double valor;
    private int rodada;

    public Aposta(int numeroApostado, String corApostada, String tipoApostado, double valor, int rodada) {
        this.numeroApostado = numeroApostado;
        this.corApostada = corApostada;
        this.tipoApostado = tipoApostado;
        this.valor = valor;
        this.rodada = rodada;
    }

    public int getNumeroApostado() { return numeroApostado; }
    public String getCorApostada() { return corApostada; }
    public String getTipoApostado() { return tipoApostado; }
    public double getValor() { return valor; }
    public int getRodada() { return rodada; }
}