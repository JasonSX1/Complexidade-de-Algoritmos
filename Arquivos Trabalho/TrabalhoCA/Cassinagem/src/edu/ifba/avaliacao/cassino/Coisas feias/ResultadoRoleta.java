package edu.ifba.avaliacao.cassino.impl;

public class ResultadoRoleta {
    private int numero;
    private String cor;
    private String tipo; // Ímpar ou Par

    public ResultadoRoleta(int numero, String cor, String tipo) {
        this.numero = numero;
        this.cor = cor;
        this.tipo = tipo;
    }

    public int getNumero() { return numero; }
    public String getCor() { return cor; }
    public String getTipo() { return tipo; }
}