package br.edu.ifba.cassino.servidor.modelo;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Jogador {
    private int id;
    private String nome;
    private String sobrenome;
    private double saldoInicial;
    private double saldo;

    //Todas as operações realizadas dentro da classe são O(1), pois envolvem apenas atribuições, leituras de atributos e concatenações de strings de tamanho fixo.

    // Construtor vazio necessário para a desserialização do JSON
    public Jogador() {}

    public Jogador(int id, String nome, String sobrenome, double saldoInicial, double saldo) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
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

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNomeCompleto() {
        return nome + " " + sobrenome;
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
        return String.format("Jogador{id=%d, nome='%s', sobrenome='%s', saldoInicial=%.2f, saldo=%.2f}",
                id, nome, sobrenome, saldoInicial, saldo);
    }
}
