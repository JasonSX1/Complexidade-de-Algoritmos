package br.edu.ifba.cassino.servidor.modelo;

public class JogadorDTO {
    private int id;
    private String nome;
    private double saldoInicial;
    private double saldoFinal;
    private String mesaId;

    public JogadorDTO(int id, String nome, double saldoInicial, double saldoFinal, String mesaId) {
        this.id = id;
        this.nome = nome;
        this.saldoInicial = saldoInicial;
        this.saldoFinal = saldoFinal;
        this.mesaId = mesaId;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public double getSaldoFinal() {
        return saldoFinal;
    }

    public String getMesaId() {
        return mesaId;
    }
}
