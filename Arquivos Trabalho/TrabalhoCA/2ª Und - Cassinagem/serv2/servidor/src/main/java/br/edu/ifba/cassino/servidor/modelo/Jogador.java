package br.edu.ifba.cassino.servidor.modelo;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Jogador {
    private int id;
    private String nome;
    private double saldoInicial;
    private double saldo;

    // Construtor vazio necessário para a desserialização do JSON
    public Jogador() {}

    public Jogador(int id, String nome, double saldoInicial, double saldo) {
        this.id = id;
        this.nome = nome;
        this.saldoInicial = saldoInicial;
        this.saldo = saldo;
    }

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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Jogador{id=" + id + ", nome='" + nome + "', saldoInicial=" + saldoInicial + ", saldo=" + saldo + "}";
    }
}
