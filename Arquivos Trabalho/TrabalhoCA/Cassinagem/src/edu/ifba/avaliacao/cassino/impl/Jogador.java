package edu.ifba.avaliacao.cassino.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogador {
    private int id;
    private String nome;
    private String sobrenome;
    private double saldo;

    // Construtor
    public Jogador(int id, String nome, String sobrenome, double saldo) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.saldo = saldo;
    }

    // Método para gerar uma quantidade 'n' de jogadores com nomes, sobrenomes e saldo aleatórios e imprimir cada um
    public static void gerarJogadores(int quantidade) {
        List<Jogador> jogadores = new ArrayList<>();
        Random random = new Random();
        String[] nomes = {"André", "João", "Carlos", "Ana", "Mariana", "Pedro", "Paula", "Ricardo", "Lucas", "Fernanda", "Gabriela", "Pablo"};
        String[] sobrenomes = {"Silva", "Pereira", "Oliveira", "Souza", "Lima", "Ferreira", "Alves", "Ribeiro", "Costa", "Melo"};

        for (int i = 1; i <= quantidade; i++) {
            String nomeAleatorio = nomes[random.nextInt(nomes.length)];
            String sobrenomeAleatorio = sobrenomes[random.nextInt(sobrenomes.length)];
            double saldoInicialAleatorio = random.nextDouble() * 1000;
            Jogador jogador = new Jogador(i, nomeAleatorio, sobrenomeAleatorio, saldoInicialAleatorio);
            jogadores.add(jogador);
            
            System.out.printf("Jogador: id: %d, Nome: %s %s, Saldo: %.2f\n", 
                              jogador.id, jogador.nome, jogador.sobrenome, jogador.saldo);
        }
    }
}