package edu.ifba.avaliacao.cassino.impl;
public class Aposta {
    private double entrada;   // Valor apostado
    private int numeroApostado;  // Número apostado
    private int numeroRoleta;     // Número sorteado na roleta
    private String corRoleta;     // Cor do número sorteado (vermelho/preto)
    private String paridadeRoleta; // Tipo (ímpar/par)
    private double resultado;     // Valor de lucro ou prejuízo

    // Construtor da aposta
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
        
        return String.format("%d\t| %.2f\t| %d\t| %d - %s - %s\t| %s\t| %.2f",
                1, entrada, numeroApostado, numeroRoleta, corRoleta, paridadeRoleta, resultadoFormatado, getSaldoResultante(entrada));
    }
}
