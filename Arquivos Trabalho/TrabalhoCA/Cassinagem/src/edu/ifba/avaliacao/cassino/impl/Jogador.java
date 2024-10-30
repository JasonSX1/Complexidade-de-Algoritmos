package edu.ifba.avaliacao.cassino.impl;

import edu.ifba.avaliacao.cassino.sensoriamento.SensorDeApostas;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogador {
    private int id;
    private String nome;
    private String sobrenome;
    private double saldo;
    private List<Aposta> historicoApostas;

    public Jogador(int id, String nome, String sobrenome, double saldo) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.saldo = saldo;
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
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Aposta> getHistoricoApostas() {
        return historicoApostas;
    }

    public void adicionarAposta(Aposta aposta) {
        historicoApostas.add(aposta);
        saldo += aposta.getResultado(); // Atualiza saldo com o resultado da aposta
    }

    public static List<Jogador> gerarJogadores(int quantidade) {
        List<Jogador> jogadores = new ArrayList<>();
        Random random = new Random();
        String[] nomes = {"André", "João", "Maria", "Carla", "Pedro"};
        String[] sobrenomes = {"Silva", "Santos", "Pereira", "Oliveira", "Lima"};

        for (int i = 0; i < quantidade; i++) {
            String nome = nomes[random.nextInt(nomes.length)];
            String sobrenome = sobrenomes[random.nextInt(sobrenomes.length)];
            double saldo = 100 + (900 * random.nextDouble());
            jogadores.add(new Jogador(i + 1, nome, sobrenome, saldo));
        }
        return jogadores;
    }

    public void apostar() {
        System.out.printf("\nJogador: %s (ID: %d)\n", getNomeCompleto(), getId());
        System.out.println("RODADA | ENTRADA | APOSTA | RESULTADO DA ROLETA | RESULTADO | SALDO RESULTANTE");

        for (Aposta aposta : historicoApostas) {
            System.out.println(SensorDeApostas.formatarResultadoAposta(aposta, this));
        }

        System.out.printf("Saldo final de %s: %s\n",
                getNomeCompleto(),
                SensorDeApostas.formatarSaldoFinal(getSaldo(), getSaldoInicial()));
    }
}
