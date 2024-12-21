package br.edu.ifba.cassino.cliente.modelo;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private int id;
    private String nome;
    private double saldoInicial;
    private double saldoAtual;
    private int totalApostas;
    private List<Aposta> historicoApostas;
    private List<String> historicoFormatado; // Para armazenar o histórico formatado
    private String mesaId; // Identificação da mesa

    public Jogador(int id, String nome, double saldoInicial, int totalApostas) {
        this.id = id;
        this.nome = nome;
        this.saldoInicial = saldoInicial;
        this.saldoAtual = saldoInicial;
        this.totalApostas = totalApostas;
        this.historicoApostas = new ArrayList<>();
        this.historicoFormatado = new ArrayList<>();
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

    public int getTotalApostas() {
        return totalApostas;
    }

    public List<Aposta> getHistoricoApostas() {
        return historicoApostas;
    }

    public List<String> getHistoricoFormatado() {
        return historicoFormatado;
    }

    public void adicionarAposta(Aposta aposta) {
        historicoApostas.add(aposta);
        saldoAtual += aposta.getResultado();
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

    public void setSaldoFinal(double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public double getSaldoFinal() {
        return saldoAtual;
    }

    public double getLucro() {
        return saldoAtual - saldoInicial;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Nome: %s, Saldo Inicial: %.2f, Saldo Atual: %.2f, Total de Apostas: %d",
                id, nome, saldoInicial, saldoAtual, totalApostas);
    }
}
