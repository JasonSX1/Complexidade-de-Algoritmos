package br.edu.ifba.cassino.servidor.modelo;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private int id;
    private String nome;
    private double saldoInicial;
    private double saldoAtual;
    private double saldoFinal;
    private int totalApostas;
    private List<String> historicoFormatado = new ArrayList<>();
    private String mesaId;

    // Construtor Padrão
    public Jogador() {}

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public double getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public int getTotalApostas() {
        return totalApostas;
    }

    public void setTotalApostas(int totalApostas) {
        this.totalApostas = totalApostas;
    }

    public List<String> getHistoricoFormatado() {
        return historicoFormatado;
    }

    public void setHistoricoFormatado(List<String> historicoFormatado) {
        this.historicoFormatado = historicoFormatado;
    }

    public String getMesaId() {
        return mesaId;
    }

    public void setMesaId(String mesaId) {
        this.mesaId = mesaId;
    }

    // Método para calcular o lucro
    public double getLucro() {
        return saldoFinal - saldoInicial;
    }

    @Override
    public String toString() {
        return "Jogador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", saldoInicial=" + saldoInicial +
                ", saldoAtual=" + saldoAtual +
                ", saldoFinal=" + saldoFinal +
                ", totalApostas=" + totalApostas +
                ", historicoFormatado=" + historicoFormatado +
                ", mesaId='" + mesaId + '\'' +
                '}';
    }
}
