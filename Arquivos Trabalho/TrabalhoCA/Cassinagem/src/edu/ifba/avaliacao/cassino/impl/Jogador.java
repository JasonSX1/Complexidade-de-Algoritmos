package edu.ifba.avaliacao.cassino.impl;

import java.util.ArrayList;

public class Jogador {
    // id, nome, sobrenome, saldo, listaDeApostas

    private int id;
    private String nome;
    private String sobrenome;
    private double saldo;
    private ArrayList<Aposta> apostas;

    public Jogador(int id, String nome, String sobrenome, double saldo) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.saldo = saldo;
        this.apostas = new ArrayList<>();
    }

    public void realizarAposta(Aposta aposta) {
        apostas.add(aposta);
    }

    public double getSaldo() {
        return saldo;
    }

    public void atualizarSaldo(double saldo) {
        this.saldo = saldo;
    }
}
