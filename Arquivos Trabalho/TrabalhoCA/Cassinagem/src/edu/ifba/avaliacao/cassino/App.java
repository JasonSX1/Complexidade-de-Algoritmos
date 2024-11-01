package edu.ifba.avaliacao.cassino;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import edu.ifba.avaliacao.cassino.impl.Jogador;
import edu.ifba.avaliacao.cassino.impl.OperacoesImpl;
import edu.ifba.avaliacao.cassino.impl.Roleta;
import edu.ifba.avaliacao.cassino.operacoes.Operacoes;
import edu.ifba.avaliacao.cassino.sensoriamento.SensorDeApostas;
import edu.ifba.avaliacao.cassino.impl.Aposta;

public class App {
        public static void main(String[] args) throws Exception {
                int quantidadeJogadores = 10;
                int rodadas = 10;

                // Instancia a classe que implementa as operações
                Operacoes operacoes = new OperacoesImpl();

                // d.1 Imprime jogadores e seus saldos iniciais
                System.out.println("Lista de Jogadores e Saldos Iniciais:");
                operacoes.imprimirJogadores(quantidadeJogadores);

                Roleta roleta = new Roleta();

                // d.2 Realiza as apostas e exibe o saldo dos jogadores após as rodadas
                operacoes.imprimirJogadoresApostas(roleta, rodadas);

                // d.2.1 Exibe o resultado final de cada jogador
                operacoes.exibirResultadoFinal();
        }

}
