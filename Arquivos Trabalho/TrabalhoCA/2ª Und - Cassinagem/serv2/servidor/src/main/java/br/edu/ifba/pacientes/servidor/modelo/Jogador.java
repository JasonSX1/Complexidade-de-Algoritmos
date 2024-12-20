package br.edu.ifba.pacientes.servidor.modelo;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private int id;
    private String nome;
    private double saldoInicial;
    private double saldoFinal;
    private double lucro;
    private List<String> historicoApostas;

    public Jogador() {}

    public Jogador(int id, String nome, double saldoInicial, double saldoFinal, double lucro, List<String> historicoApostas) {
        this.id = id;
        this.nome = nome;
        this.saldoInicial = saldoInicial;
        this.saldoFinal = saldoFinal;
        this.lucro = lucro;
        this.historicoApostas = historicoApostas;
    }

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

    public double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public double getLucro() {
        return lucro;
    }

    public void setLucro(double lucro) {
        this.lucro = lucro;
    }

    public List<String> getHistoricoApostas() {
        return historicoApostas;
    }

    public void setHistoricoApostas(List<String> historicoApostas) {
        this.historicoApostas = historicoApostas;
    }
}