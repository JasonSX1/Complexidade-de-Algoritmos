package edu.ifba.avaliacao.cassino;

import edu.ifba.avaliacao.cassino.impl.OperacoesImpl;
import edu.ifba.avaliacao.cassino.operacoes.Operacoes;

public class App {
        public static void main(String[] args) throws Exception {
                int quantidadeJogadores = 10;
                int rodadas = 10;

                // Instancia a classe que implementa as operações
                Operacoes operacoes = new OperacoesImpl();

                // d.1 Imprime jogadores e seus saldos iniciais
                operacoes.gerarJogadores(quantidadeJogadores);

                // d.2 Realiza as apostas e exibe o saldo dos jogadores após as rodadas
                operacoes.gerarApostas(rodadas);

                // d.2.1 Exibe o resultado final de cada jogador
                operacoes.resultadoAposApostas();

                //operacoes.ordenarApostas();
        }

}
