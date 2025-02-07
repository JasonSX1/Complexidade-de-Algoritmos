package br.edu.ifba.cassino.cliente.modelo;

import br.edu.ifba.cassino.cliente.sensoriamento.SensorDeApostas;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    // Gera uma lista de jogadores com nomes e sobrenomes aleatórios usando Java Faker
    public static List<Jogador> gerarJogadores(int quantidade) {
        List<Jogador> jogadores = new ArrayList<>();
        Faker faker = new Faker();
        Random random = new Random();

        System.out.println("[GERAÇÃO DE JOGADORES] Criando " + quantidade + " jogadores...");

        for (int i = 0; i < quantidade; i++) {
            String nome = faker.name().firstName();  // Nome aleatório
            String sobrenome = faker.name().lastName();  // Sobrenome aleatório
            double saldo = 1000 + (random.nextDouble() * 4000); // Saldo inicial entre 1000 e 5000

            Jogador jogador = new Jogador(i + 1, nome, sobrenome, saldo);
            jogadores.add(jogador);

            System.out.printf("[JOGADOR CRIADO] ID: %d, Nome: %s, Sobrenome: %s, Saldo Inicial: %.2f\n",
                    jogador.getId(), jogador.nome, jogador.sobrenome, jogador.getSaldoInicial());
        }

        System.out.println("[DEBUG] Total de jogadores criados: " + jogadores.size());

        return jogadores;
    }

    // Método para realizar as apostas do jogador
    public void apostar() {
        System.out.printf("\nApostas de %s:\n", getNomeCompleto());
        System.out.println(
                "RODADA | TIPO     | ENTRADA | APOSTA   | RESULTADO DA ROLETA     | RESULTADO | SALDO RESULTANTE");
        System.out.println("");

        double saldoAtual = saldoInicial; // Inicializa o saldo com o valor inicial para acompanhar as atualizações

        for (Aposta aposta : historicoApostas) {
            // Atualiza o saldo atual do jogador com o resultado da aposta
            saldoAtual += aposta.getResultado();

            // Usa o método formatarResultadoAposta para formatar o resultado da rodada
            String resultadoFormatado = SensorDeApostas.formatarResultadoAposta(aposta, saldoAtual);

            // Exibe o resultado formatado
            System.out.println(resultadoFormatado);
        }
    }
}
