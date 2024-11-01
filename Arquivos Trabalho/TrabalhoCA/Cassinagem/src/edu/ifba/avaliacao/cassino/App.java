package edu.ifba.avaliacao.cassino;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import edu.ifba.avaliacao.cassino.impl.Jogador;
import edu.ifba.avaliacao.cassino.sensoriamento.SensorDeApostas;
import edu.ifba.avaliacao.cassino.impl.Aposta;

public class App {
    public static void main(String[] args) throws Exception {
        int quantidadeJogadores = 10; // Quantidade padrão de jogadores
        int rodadas = 10; // Define a quantidade de apostas para cada jogador

        // Código ANSI para vermelho
        final String VERMELHO = "\u001B[31m";
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

        // Implementação d.1 - imprime os jogadores e seus saldos iniciais
        List<Jogador> jogadores = Jogador.gerarJogadores(quantidadeJogadores);

        // Implementação d.2 - imprime a lista de resultados da roleta e as apostas
        // associadas a cada jogador
        SensorDeApostas.gerarApostasParaJogadores(jogadores, rodadas);
    }

}
