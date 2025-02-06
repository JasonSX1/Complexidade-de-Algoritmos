// Classe que representa um Jogador no sistema
package br.edu.ifba.cassino.servidor.modelo;

public class Jogador {
    private String nome;
    private double saldo;

    public Jogador(String nome, double saldo) {
        this.nome = nome;
        this.saldo = saldo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Jogador{" +
                "nome='" + nome + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}