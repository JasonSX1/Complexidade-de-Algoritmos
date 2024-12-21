package br.edu.ifba.cassino.cliente.modelo;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private int id;
    private String nome;
    private double saldoInicial;
    private double saldoAtual;
    private double saldoFinal;
    private int totalApostas;
    private List<Aposta> historicoApostas;
    private List<String> historicoFormatado;
    private String mesaId; // Identificação da mesa
    private boolean terminouApostas; // Variavel de controle de status do jogador

    public Jogador(int id, String nome, double saldoInicial, int totalApostas) {
        this.id = id;
        this.nome = nome;
        this.saldoInicial = Math.round(saldoInicial * 100.0) / 100.0;
        this.saldoAtual = this.saldoInicial;
        this.saldoFinal = 0.0;
        this.totalApostas = totalApostas;
        this.historicoApostas = new ArrayList<>();
        this.historicoFormatado = new ArrayList<>();
        this.mesaId = null;
    }

    public boolean isTerminouApostas() {
        return terminouApostas;
    }
    
    public void setTerminouApostas(boolean terminouApostas) {
        this.terminouApostas = terminouApostas;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public double getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(double saldoAtual) {
        this.saldoAtual = Math.max(0, Math.round(saldoAtual * 100.0) / 100.0);
    }

    public double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(double saldoFinal) {
        this.saldoFinal = Math.max(0, Math.round(saldoFinal * 100.0) / 100.0);
    }

    public int getTotalApostas() {
        return totalApostas;
    }

    public void setTotalApostas(int totalApostas) {
        this.totalApostas = totalApostas;
    }

    public List<Aposta> getHistoricoApostas() {
        return historicoApostas;
    }

    public void adicionarAposta(Aposta aposta) {
        historicoApostas.add(aposta);
    }

    public List<String> getHistoricoFormatado() {
        return historicoFormatado;
    }

    public void adicionarHistoricoAposta(String historico) {
        historicoFormatado.add(historico);
    }

    public String getMesaId() {
        return mesaId;
    }

    public void setMesaId(String mesaId) {
        this.mesaId = mesaId;
    }

    public double getLucro() {
        return Math.round((saldoFinal - saldoInicial) * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %d, Nome: %s, Saldo Inicial: %.2f, Saldo Atual: %.2f, Saldo Final: %.2f, Total de Apostas: %d",
            id, nome, saldoInicial, saldoAtual, saldoFinal, totalApostas
        );
    }
}
