package edu.ifba.avaliacao.cassino.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogador {
    private static final double SALDO_MINIMO = 100.0; // Valor mínimo para o saldo inicial
    private static final double SALDO_MAXIMO = 1000.0; // Valor máximo para o saldo inicial
    private int id;
    private String nome;
    private String sobrenome;
    private double saldo;
    private List<Aposta> historicoApostas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nome + " " + sobrenome;
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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Aposta> getHistoricoApostas() {
        return historicoApostas;
    }

    // Construtor do jogador
    public Jogador(int id, String nome, String sobrenome, double saldo) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.saldo = saldo;
        this.historicoApostas = new ArrayList<>(); // Inicializa a lista de apostas
    }

    // Geração de jogadores com nomes e saldo aleatório
    public static List<Jogador> gerarJogadores(int quantidade) {
        List<Jogador> jogadores = new ArrayList<>();
        Random random = new Random();
        String[] nomes = { "André", "João", "Maria", "Carla", "Pedro", "Ana", "Lucas", "Beatriz", "Paulo", "Sara" };
        String[] sobrenomes = { "Silva", "Santos", "Pereira", "Oliveira", "Lima", "Costa", "Almeida", "Ribeiro",
                "Martins", "Barbosa" };

        for (int i = 0; i < quantidade; i++) {
            String nome = nomes[random.nextInt(nomes.length)];
            String sobrenome = sobrenomes[random.nextInt(sobrenomes.length)];
            double saldo = SALDO_MINIMO + (SALDO_MAXIMO - SALDO_MINIMO) * random.nextDouble();
            jogadores.add(new Jogador(i + 1, nome, sobrenome, saldo));
        }
        imprimirJogadores(jogadores); // Imprime a lista de jogadores gerada
        return jogadores;
    }

    // Método de impressão dos jogadores monitorados
    public static void imprimirJogadores(List<Jogador> jogadores) {
        System.out.println("Lista de jogadores monitorados:");
        for (Jogador jogador : jogadores) {
            System.out.printf("Jogador apostando: id: %d, Nome: %s %s, Saldo: %.2f\n",
                    jogador.id, jogador.nome, jogador.sobrenome, jogador.saldo);
        }
    }

    public void adicionarAposta(Aposta aposta) {
        historicoApostas.add(aposta);
        saldo = aposta.getSaldoResultante(saldo); // Atualiza saldo com o resultado da aposta
    }

    public double getSaldoInicial() {
        return saldo; // Retorna o saldo inicial (ou pode-se implementar uma lógica específica para isso)
    }

    public void imprimirLeiturasPorRodada() {
        System.out.println("RODADA | ENTRADA | APOSTA | RESULTADO DA ROLETA   | RESULTADO | SALDO RESULTANTE");
        for (int i = 0; i < historicoApostas.size(); i++) {
            Aposta aposta = historicoApostas.get(i);
            System.out.println((i + 1) + "\t| " + aposta);
        }
    }
}
