package edu.ifba.avaliacao.cassino.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogador {
    private int id;
    private String nome;
    private String sobrenome;
    private double saldo;
    private List<Aposta> apostas;

    public Jogador(int id, String nome, String sobrenome, double saldo) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.saldo = saldo;
        this.apostas = new ArrayList<>();
    }

    public void adicionarAposta(Aposta aposta) {
        apostas.add(aposta);
    }

    public List<Aposta> getApostas() { return apostas; }
    public String getNome() { return nome; }
    public String getSobrenome() { return sobrenome; }
    public double getSaldo() { return saldo; }

    @Override
    public String toString() {
        return "Jogador id: " + id + ", Nome: " + nome + " " + sobrenome + ", Saldo: R$" + saldo;
    }
}