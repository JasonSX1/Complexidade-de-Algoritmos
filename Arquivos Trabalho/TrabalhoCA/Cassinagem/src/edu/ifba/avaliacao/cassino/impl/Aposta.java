package edu.ifba.avaliacao.cassino.impl;

public class Aposta {
    private double entrada;         // Valor apostado
    private int numeroApostado;     // Número apostado
    private int numeroRoleta;       // Número sorteado na roleta
    private String corRoleta;       // Cor do número sorteado (vermelho/preto)
    private String paridadeRoleta;  // Tipo (ímpar/par)
    private double resultado;       // Valor de lucro ou prejuízo

    // Construtor da aposta
    public Aposta(double entrada, int numeroApostado, int numeroRoleta, String corRoleta, String paridadeRoleta, double resultado) {
        this.entrada = entrada;
        this.numeroApostado = numeroApostado;
        this.numeroRoleta = numeroRoleta;
        this.corRoleta = corRoleta;
        this.paridadeRoleta = paridadeRoleta;
        this.resultado = resultado;
    }

    public double getEntrada() {
        return entrada;
    }

    public int getNumeroApostado() {
        return numeroApostado;
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

    public double getResultado() {
        return resultado;
    }

    public double getSaldoResultante(double saldoAtual) {
        return saldoAtual + resultado;
    }

    @Override
    public String toString() {
        String VERDE = "\u001B[32m";
        String VERMELHO = "\u001B[31m";
        String RESET = "\u001B[0m";
        
        // Formata o resultado para verde ou vermelho, dependendo do valor
        String resultadoFormatado = (resultado >= 0 ? VERDE : VERMELHO) + String.format("%.2f", resultado) + RESET;
        
        return String.format("%.2f\t| %d\t| %d - %s - %s\t| %s",
                entrada, numeroApostado, numeroRoleta, corRoleta, paridadeRoleta, resultadoFormatado);
    }
}
