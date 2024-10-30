package edu.ifba.avaliacao.cassino;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import edu.ifba.avaliacao.cassino.impl.Jogador;
import edu.ifba.avaliacao.cassino.sensoriamento.SensorDeApostas;

public class App {
    public static void main(String[] args) throws Exception {
        int quantidadeJogadores = 10; // Quantidade padrão de jogadores
        int rodadas = 10;  // Define a quantidade de apostas para cada jogador

        // Código ANSI para verde
        final String VERDE = "\u001B[32m";
        // Código ANSI para vermelho
        final String VERMELHO = "\u001B[31m";
        // Código ANSI para preto
        final String PRETO = "\u001B[30m";
        // Código ANSI para resetar a cor
        final String RESET = "\u001B[0m";

        System.out.println(
                VERMELHO + "▄▀▄▄▄▄   ▄▀▀█▄   ▄▀▀▀▀▄  ▄▀▀▀▀▄  ▄▀▀█▀▄    ▄▀▀▄ ▀▄  ▄▀▀█▄   ▄▀▀▀▀▄   ▄▀▀█▄▄▄▄  ▄▀▀▄ ▄▀▄");
        System.out.println(
                VERMELHO + "█ █    ▀ ▀ ▄▀ ▀▄ █ █   ▀ █ █   ▀ █   █  █  █  █ █ █ ▀ ▄▀ ▀▄ █        ▀  ▄▀   ▀ █  █ ▀  █ ");
        System.out.println(
                VERMELHO + "▀ █        █▄▄▄█    ▀▄      ▀▄   ▀   █  ▀  ▀  █  ▀█   █▄▄▄█ █    ▀▄▄   █▄▄▄▄▄  ▀  █    █ ");
        System.out.println(
                VERMELHO + "  █       ▄▀   █ ▀▄   █  ▀▄   █      █       █   █   ▄▀   █ █     █ █  █    ▀    █    █");
        System.out.println(
                VERMELHO + " ▄▀▄▄▄▄▀ █   ▄▀   █▀▀▀    █▀▀▀    ▄▀▀▀▀▀▄  ▄▀   █   █   ▄▀  ▀▀▄▄▄▄▀ ▀ ▄▀▄▄▄▄   ▄▀   ▄▀  ");
        System.out.println(
                VERMELHO + "█     ▀  ▀   ▀    ▀       ▀      █       █ █    ▀   ▀   ▀   ▀         █    ▀   █    █    ");
        System.out.println(
                VERMELHO + "▀                                ▀       ▀ ▀                          ▀        ▀    ▀    ");
        System.out.println(RESET);

        // d.1 imprimindo os Jogadores
        System.out.println("Imprimindo os Jogadores...\n");
        List<Jogador> jogadores = Jogador.gerarJogadores(quantidadeJogadores);

        // Geração das apostas para os jogadores e exibição dos resultados
        SensorDeApostas.gerarApostasParaJogadores(jogadores, rodadas);


        // Geração das apostas para os jogadores e exibição dos resultados
        SensorDeApostas.gerarApostasParaJogadores(jogadores, rodadas);

        // Imprime o saldo final de cada jogador com formatação condicional
        System.out.println("\nSaldo final dos jogadores:");
        for (Jogador jogador : jogadores) {
            double saldoFinal = jogador.getSaldo();
            String saldoFormatado = (saldoFinal >= jogador.getSaldoInicial() ? VERDE : VERMELHO) + String.format("%.2f", saldoFinal) + RESET;
            System.out.printf("Jogador: %s - Saldo Final: %s\n", jogador.getNomeCompleto(), saldoFormatado);
        }
    }

}
