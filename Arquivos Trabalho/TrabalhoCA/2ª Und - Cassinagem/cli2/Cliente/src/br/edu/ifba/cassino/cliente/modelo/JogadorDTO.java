package br.edu.ifba.cassino.cliente.modelo;

public class JogadorDTO {
    private int id;
    private String nome;
    private double saldoInicial;
    private double saldoFinal;
    private String mesaId; // Identificador da mesa

    public JogadorDTO(int id, String nome, double saldoInicial, double saldoFinal, String mesaId) {
        this.id = id;
        this.nome = nome;
        this.saldoInicial = Math.round(saldoInicial * 100.0) / 100.0;
        this.saldoFinal = Math.round(saldoFinal * 100.0) / 100.0;
        this.mesaId = mesaId != null ? mesaId : "Não especificado"; // Garante um valor padrão
    }

    // Getters
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

    @Override
    public String toString() {
        return String.format(
            "ID: %d, Nome: %s, Saldo Inicial: %.2f, Saldo Final: %.2f, Mesa: %s",
            id, nome, saldoInicial, saldoFinal, mesaId
        );
    }
}
