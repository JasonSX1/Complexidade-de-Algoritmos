package br.edu.ifba.avaliacao.cliente.modelo;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private int id;
    private String nome;
    private double saldoInicial;
    private double saldoAtual;
    private int totalApostas;
    private List<Aposta> historicoApostas;

    public Jogador(int id, String nome, double saldoInicial, int totalApostas) {
        this.id = id;
        this.nome = nome;
        this.saldoInicial = saldoInicial;
        this.saldoAtual = saldoInicial;
        this.totalApostas = totalApostas;
        this.historicoApostas = new ArrayList<>();
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

    public void adicionarAposta(Aposta aposta) {
        historicoApostas.add(aposta);
        saldoAtual += aposta.getResultado();
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Nome: %s, Saldo Inicial: %.2f, Saldo Atual: %.2f, Total de Apostas: %d",
                             id, nome, saldoInicial, saldoAtual, totalApostas);
    }
}
