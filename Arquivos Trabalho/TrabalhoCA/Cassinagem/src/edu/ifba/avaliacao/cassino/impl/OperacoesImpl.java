package edu.ifba.avaliacao.cassino.impl;

import edu.ifba.avaliacao.cassino.operacoes.Operacoes;
import edu.ifba.avaliacao.cassino.sensoriamento.SensorDeApostas;
import java.util.List;

public class OperacoesImpl implements Operacoes {
    private List<Jogador> jogadores;

    @Override
    public void imprimirJogadores(int quantidade) {
        // Gera jogadores e armazena a lista
        jogadores = Jogador.gerarJogadores(quantidade);

        // Imprime os jogadores e seus saldos iniciais
        for (Jogador jogador : jogadores) {
            System.out.printf("Jogador: %s - Saldo inicial: %.2f\n", jogador.getNomeCompleto(), jogador.getSaldo());
        }
    }

    @Override
    public void imprimirJogadoresApostas(Roleta roleta, int rodadas) {
        // Executa as rodadas e gera apostas para os jogadores
        SensorDeApostas.gerarApostasParaJogadores(jogadores, rodadas);

        // Exibe as apostas e os resultados intermediários
        System.out.println("\nApostas e Resultados das Rodadas:");
        for (Jogador jogador : jogadores) {
            System.out.printf("Jogador: %s - Saldo após apostas: %.2f\n", jogador.getNomeCompleto(), jogador.getSaldo());
        }
    }

    @Override
    public void exibirResultadoFinal() {
        // Exibe o saldo final de cada jogador após todas as rodadas
        System.out.println("\nSaldo final de cada jogador:");
        for (Jogador jogador : jogadores) {
            System.out.printf("Jogador: %s - Saldo final: %.2f\n", jogador.getNomeCompleto(), jogador.getSaldo());
        }
    }
}
