package edu.ifba.avaliacao.cassino;

import java.io.IOException;
import java.util.Scanner;

import edu.ifba.avaliacao.cassino.impl.Jogador;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        int op;
        final int QTD_JOGADORES = 10;

        // Código ANSI para verde
        final String VERDE = "\u001B[32m";
        // Código ANSI para vermelho
        final String VERMELHO = "\u001B[31m";
        // Código ANSI para preto
        final String PRETO = "\u001B[30m";
        // Código ANSI para resetar a cor
        final String RESET = "\u001B[0m";

        System.out.println(VERMELHO + "▄▀▄▄▄▄   ▄▀▀█▄   ▄▀▀▀▀▄  ▄▀▀▀▀▄  ▄▀▀█▀▄    ▄▀▀▄ ▀▄  ▄▀▀█▄   ▄▀▀▀▀▄   ▄▀▀█▄▄▄▄  ▄▀▀▄ ▄▀▄");
        System.out.println(VERMELHO + "█ █    ▀ ▀ ▄▀ ▀▄ █ █   ▀ █ █   ▀ █   █  █  █  █ █ █ ▀ ▄▀ ▀▄ █        ▀  ▄▀   ▀ █  █ ▀  █ ");
        System.out.println(VERMELHO + "▀ █        █▄▄▄█    ▀▄      ▀▄   ▀   █  ▀  ▀  █  ▀█   █▄▄▄█ █    ▀▄▄   █▄▄▄▄▄  ▀  █    █ ");
        System.out.println(VERMELHO + "  █       ▄▀   █ ▀▄   █  ▀▄   █      █       █   █   ▄▀   █ █     █ █  █    ▀    █    █");
        System.out.println(VERMELHO + " ▄▀▄▄▄▄▀ █   ▄▀   █▀▀▀    █▀▀▀    ▄▀▀▀▀▀▄  ▄▀   █   █   ▄▀  ▀▀▄▄▄▄▀ ▀ ▄▀▄▄▄▄   ▄▀   ▄▀  ");
        System.out.println(VERMELHO + "█     ▀  ▀   ▀    ▀       ▀      █       █ █    ▀   ▀   ▀   ▀         █    ▀   █    █    ");
        System.out.println(VERMELHO + "▀                                ▀       ▀ ▀                          ▀        ▀    ▀    ");
        System.out.println(RESET);

        // d.1 imprimindo os Jogadores
        System.out.println("Imprimindo os Jogadores...\n");
        //Jogador.gerarJogadores(QTD_JOGADORES);

        // d.2 imprimindo as apostas por jogador
    }

}
