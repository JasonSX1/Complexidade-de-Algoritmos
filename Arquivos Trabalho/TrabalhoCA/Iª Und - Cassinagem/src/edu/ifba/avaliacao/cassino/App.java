package edu.ifba.avaliacao.cassino;

import edu.ifba.avaliacao.cassino.impl.OperacoesImpl;
import edu.ifba.avaliacao.cassino.operacoes.Operacoes;

public class App {
        public static void main(String[] args) throws Exception {
                int quantidadeJogadores = 10;
                int rodadas = 10;

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


                // Instanciação da classe que implementa as operações
                Operacoes operacoes = new OperacoesImpl();

                // d.1 - Imprime jogadores e seus saldos iniciais
                operacoes.gerarJogadores(quantidadeJogadores);

                System.out.println(
                                "/----------------------------------------------------------------------------------------------/");

                // d.2 - Realiza as apostas e exibe o saldo dos jogadores após as rodadas
                operacoes.gerarApostas(rodadas);

                System.out.println(
                                "/----------------------------------------------------------------------------------------------/");

                // d.2.1 - Exibe o resultado final de cada jogador
                operacoes.resultadoAposApostas();

                System.out.println(
                                "/----------------------------------------------------------------------------------------------/");

                // d.3 - Ordena as apostas por lucro e exibe
                operacoes.ordenarApostas();

                System.out.println(
                                "/----------------------------------------------------------------------------------------------/");

                // d.4 - Calcula os melhores resultados para grupos de 3 jogadores e retorna o
                // grupo com maior lucro
                operacoes.calcularMelhoresResultadosGruposDeTres();

        }

}
