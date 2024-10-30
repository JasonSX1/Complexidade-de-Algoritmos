package edu.ifba.avaliacao.cassino.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.ifba.avaliacao.cassino.sensoriamento.*;

public class Jogador {
    private int id;
    private String nome;
    private String sobrenome;
    private double saldo;
    private double saldoInicial;
    private List<Aposta> historicoApostas;

    public Jogador(int id, String nome, String sobrenome, double saldo) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.saldo = saldo;
        this.saldoInicial = saldo;
        this.historicoApostas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nome + " " + sobrenome;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Aposta> getHistoricoApostas() {
        return historicoApostas;
    }

    public void adicionarAposta(Aposta aposta) {
        historicoApostas.add(aposta);
        saldo += aposta.getResultado();
    }

    public static List<Jogador> gerarJogadores(int quantidade) {
        List<Jogador> jogadores = new ArrayList<>();
        Random random = new Random();
        String[] nomes = {"Lucas", "Beatriz", "Pedro", "Maria", "Paulo", "João"};
        String[] sobrenomes = {"Silva", "Almeida", "Oliveira", "Barbosa", "Lima"};

        System.out.println("Imprimindo os Jogadores...");
        System.out.println("\nLista de jogadores monitorados:");
        for (int i = 0; i < quantidade; i++) {
            String nome = nomes[random.nextInt(nomes.length)];
            String sobrenome = sobrenomes[random.nextInt(sobrenomes.length)];
            double saldo = 100 + (900 * random.nextDouble());
            Jogador jogador = new Jogador(i + 1, nome, sobrenome, saldo);
            System.out.printf("Jogador apostando: id: %d, Nome: %s, Saldo Inicial: %.2f\n",
                    jogador.getId(), jogador.getNomeCompleto(), jogador.getSaldoInicial());
            jogadores.add(jogador);
        }
        return jogadores;
    }

    public void apostar() {
        System.out.printf("\nApostas do %s:\n", getNomeCompleto());
        System.out.println("RODADA | ENTRADA | APOSTA | RESULTADO DA ROLETA | RESULTADO | SALDO RESULTANTE");
    
        double saldoAtualizado = saldoInicial; // Começa com o saldo inicial
        for (Aposta aposta : historicoApostas) {
            saldoAtualizado += aposta.getResultado(); // Atualiza o saldo com o resultado da aposta atual
            System.out.println(SensorDeApostas.formatarResultadoAposta(aposta, saldoAtualizado));
        }
    }
    
}
