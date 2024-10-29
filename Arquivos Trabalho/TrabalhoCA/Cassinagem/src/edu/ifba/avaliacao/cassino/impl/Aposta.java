package edu.ifba.avaliacao.cassino.impl;

public class Aposta {
    private int numeroApostado;
    private String corApostada; // Vermelho/preto/verde
    private String tipoApostado; // Ímpar/Par
    private double valorAposta;

    public Aposta(int numeroApostado, String corApostada, String tipoApostado, double valor, int rodada) {
        this.numeroApostado = numeroApostado;
        this.corApostada = corApostada;
        this.tipoApostado = tipoApostado;
        this.valorAposta = valor;
    }

    public int getNumeroApostado() {
        return numeroApostado;
    }

    public String getCorApostada() {
        return corApostada;
    }

    public String getTipoApostado() {
        return tipoApostado;
    }

    public double getValor() {
        return valorAposta;
    }

    public void calcularRetorno(double retorno) {
        this.valorAposta += retorno;
    }
}