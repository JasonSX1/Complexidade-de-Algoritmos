package edu.ifba.avaliacao.cassino.impl;

public class Aposta {
    private double entrada;
    private int numeroApostado;
    private int numeroRoleta;
    private String corRoleta;
    private String paridadeRoleta;
    private double resultado;

    public Aposta(double entrada, int numeroApostado, int numeroRoleta, String corRoleta, String paridadeRoleta, double resultado) {
        this.entrada = entrada;
        this.numeroApostado = numeroApostado;
        this.numeroRoleta = numeroRoleta;
        this.corRoleta = corRoleta;
        this.paridadeRoleta = paridadeRoleta;
        this.resultado = resultado;
    }

    public double getResultado() {
        return resultado;
    }

    public int getNumeroRoleta() {
        return numeroRoleta;
    }

    public String getCorRoleta() {
        return corRoleta;
    }

    public String getParidadeRoleta() {
        return paridadeRoleta;
    }

    public double getEntrada() {
        return entrada;
    }

    public int getNumeroApostado() {
        return numeroApostado;
    }
}
