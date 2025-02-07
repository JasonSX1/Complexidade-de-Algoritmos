package br.edu.ifba.cassino.cliente.modelo;

import br.edu.ifba.cassino.cliente.sensoriamento.SensorDeApostas;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
        if (aposta == null) {
            System.err.println("[ERRO] Tentativa de adicionar uma aposta nula ao jogador " + getNomeCompleto());
            return;
        }

        historicoApostas.add(aposta);
        saldo += aposta.getResultado();

        System.out.printf(
                getNomeCompleto(), aposta.getTipoAposta(), aposta.getEntrada(), aposta.getResultado(),
                historicoApostas.size());
    }

    // Gera uma lista de jogadores com nomes e sobrenomes aleatórios usando Java
    // Faker
    public static List<Jogador> gerarJogadores(int quantidade) {
        List<Jogador> jogadores = new ArrayList<>();
        Faker faker = new Faker(new Locale("pt", "BR"));
        Random random = new Random();

        System.out.println("[GERAÇÃO DE JOGADORES] Criando " + quantidade + " jogadores...");

        for (int i = 0; i < quantidade; i++) {
            String nome = faker.name().firstName(); // Nome aleatório
            String sobrenome = faker.name().lastName(); // Sobrenome aleatório
            double saldo = 1000 + (random.nextDouble() * 4000); // Saldo inicial entre 1000 e 5000
            saldo = (int) (saldo * 100) / 100.0; // Ajuste para duas casas decimais
            Jogador jogador = new Jogador(i + 1, nome, sobrenome, saldo);
            jogadores.add(jogador);

            System.out.printf("[JOGADOR CRIADO] ID: %d, Nome: %s, Sobrenome: %s, Saldo Inicial: %.2f\n",
                    jogador.getId(), jogador.nome, jogador.sobrenome, jogador.getSaldoInicial());
        }

        return jogadores;
    }

    // Método para realizar as apostas do jogador
    public void apostar() {
        if (historicoApostas.isEmpty()) {
            System.out.println(
                    "[ERRO] Nenhuma aposta foi gerada para " + getNomeCompleto() + "! Criando aposta de teste...");
            Aposta aposta = new Aposta(50, "COR", -1, "VERMELHO", null, 10, "VERMELHO", "PAR", 75);
            adicionarAposta(aposta);
        }

        System.out.printf("\nApostas de %s:\n", getNomeCompleto());
        System.out.println(
                "RODADA | TIPO     | ENTRADA | APOSTA   | RESULTADO DA ROLETA     | RESULTADO | SALDO RESULTANTE");

        double saldoAtual = saldoInicial;

        for (Aposta aposta : historicoApostas) {
            saldoAtual += aposta.getResultado();
            String resultadoFormatado = SensorDeApostas.formatarResultadoAposta(aposta, saldoAtual);
            System.out.println(resultadoFormatado);
        }
    }

}
