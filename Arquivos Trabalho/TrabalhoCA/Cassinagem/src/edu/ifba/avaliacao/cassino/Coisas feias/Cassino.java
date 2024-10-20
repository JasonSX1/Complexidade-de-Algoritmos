package edu.ifba.avaliacao.cassino.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.ifba.avaliacao.cassino.roleta.Roleta;

public class Cassino {

    private List<Jogador> jogadores = new ArrayList<>();
    private Roleta roleta = new RoletaImpl();

    public static void main(String[] args) {
        Cassino cassino = new Cassino();
        cassino.executar();
    }

    public void executar() {
        Scanner input = new Scanner(System.in);
        System.out.println("Quantos jogadores deseja gerar? (Padrão: 10)");
        int quantidadeJogadores = input.nextInt();
        if (quantidadeJogadores <= 0) {
            quantidadeJogadores = 10;
        }

        gerarJogadores(quantidadeJogadores);
        int rodada = 1;
        
        System.out.println("\n=== Iniciando as Apostas ===");
        for (Jogador jogador : jogadores) {
            Aposta aposta = new Aposta(12, "Vermelho", "Ímpar", 50.0, rodada);
            jogador.adicionarAposta(aposta);
        }

        ResultadoRoleta resultado = roleta.sortear();

        System.out.println("\n=== Resultados da Rodada " + rodada + " ===");
        imprimirApostas(resultado);
    }

    private void gerarJogadores(int quantidade) {
        String[] nomes = {"André", "João", "Maria", "Paulo", "Ana"};
        String[] sobrenomes = {"Silva", "Pereira", "Oliveira", "Costa", "Lima"};

        for (int i = 1; i <= quantidade; i++) {
            String nome = nomes[i % nomes.length];
            String sobrenome = sobrenomes[i % sobrenomes.length];
            Jogador jogador = new Jogador(i, nome, sobrenome, 500.0);
            jogadores.add(jogador);
        }
    }

    private void imprimirApostas(ResultadoRoleta resultado) {
        for (Jogador jogador : jogadores) {
            for (Aposta aposta : jogador.getApostas()) {
                double retorno = roleta.calcularRetorno(aposta, resultado);

                System.out.println("Jogador: " + jogador.getNome() + " " + jogador.getSobrenome() +
                                   ", Aposta rodada " + aposta.getRodada() + ": R$" + aposta.getValor() +
                                   " Resultado da roleta: Número " + resultado.getNumero() +
                                   ", Cor: " + resultado.getCor() + ", Tipo: " + resultado.getTipo() +
                                   " Retorno: R$" + retorno);
            }
        }
    }
}