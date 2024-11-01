package edu.ifba.avaliacao.cassino.impl;

import edu.ifba.avaliacao.cassino.operacoes.Operacoes;
import edu.ifba.avaliacao.cassino.ordenador.OrdenadorDeApostas;
import edu.ifba.avaliacao.cassino.sensoriamento.SensorDeApostas;
import java.util.List;

public class OperacoesImpl implements Operacoes {
    private List<Jogador> jogadores;
    private OrdenadorDeApostas ordenadorDeApostas = new OrdenadorImpl(); // Instância do ordenador de apostasF

    @Override
    public void gerarJogadores(int quantidade) {
        // Gera jogadores e armazena a lista
        jogadores = Jogador.gerarJogadores(quantidade);
    }

    @Override
    public void gerarApostas(int rodadas) {
        // Executa as rodadas e gera apostas para os jogadores
        SensorDeApostas.gerarApostasParaJogadores(jogadores, rodadas);
    }

    @Override
    public void resultadoAposApostas() {
        // Exibe o saldo final de cada jogador após todas as rodadas
        SensorDeApostas.exibirSaldoFinalDosJogadores(jogadores);
    }

    @Override
    public void ordenarApostas() {
        for (Jogador jogador : jogadores) {
            List<Aposta> apostas = jogador.getHistoricoApostas();
            List<Aposta> apostasOrdenadas = ordenadorDeApostas.ordenarPorLucro(apostas);
            System.out.println(" ");
            System.out.println("Apostas ordenadas por lucro para o jogador " + jogador.getNomeCompleto() + ":");
            System.out.println(" ");
            System.out.println(
                    "RODADA | TIPO     | ENTRADA | APOSTA   | RESULTADO DA ROLETA     | RESULTADO | SALDO RESULTANTE");
            System.out.println(" ");
            double saldoAtual = jogador.getSaldoInicial();

            for (Aposta aposta : apostasOrdenadas) {
                saldoAtual += aposta.getResultado();
                String apostaFormatada = SensorDeApostas.formatarResultadoAposta(aposta, saldoAtual);
                System.out.println(apostaFormatada);
            }
        }
    }

}
