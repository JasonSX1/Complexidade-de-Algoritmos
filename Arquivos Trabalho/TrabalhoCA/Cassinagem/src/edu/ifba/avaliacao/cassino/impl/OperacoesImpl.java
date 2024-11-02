package edu.ifba.avaliacao.cassino.impl;

import edu.ifba.avaliacao.cassino.operacoes.Operacoes;
import edu.ifba.avaliacao.cassino.ordenador.OrdenadorDeApostas;
import edu.ifba.avaliacao.cassino.sensoriamento.SensorDeApostas;
import java.util.List;
import java.util.ArrayList;

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

    public void calcularMelhoresResultadosGruposDeTres() {
        int quantidadeJogadores = jogadores.size();
        double melhorLucro = Double.NEGATIVE_INFINITY;
        List<Jogador> melhorGrupo = null;
    
        // Gera todas as combinações de grupos de 3 jogadores
        for (int i = 0; i < quantidadeJogadores; i++) {
            for (int j = i + 1; j < quantidadeJogadores; j++) {
                for (int k = j + 1; k < quantidadeJogadores; k++) {
                    List<Jogador> grupo = List.of(jogadores.get(i), jogadores.get(j), jogadores.get(k));
                    double lucroGrupo = calcularLucroGrupo(grupo);
    
                    // Atualiza se encontramos um lucro melhor
                    if (lucroGrupo > melhorLucro) {
                        melhorLucro = lucroGrupo;
                        melhorGrupo = grupo;
                    }
                }
            }
        }
    
        // Imprime as melhores apostas do grupo
        System.out.printf("Melhor grupo de 3 jogadores: Lucro = %.2f\n", melhorLucro);
        if (melhorGrupo != null) {
            for (Jogador jogador : melhorGrupo) {
                System.out.println(jogador.getNomeCompleto());
                List<Aposta> apostas = jogador.getHistoricoApostas();
                for (Aposta aposta : apostas) {
                    System.out.println(SensorDeApostas.formatarResultadoAposta(aposta, jogador.getSaldo()));
                }
            }
        }
    }
    
    private double calcularLucroGrupo(List<Jogador> grupo) {
        double lucroTotal = 0;
    
        // Itera sobre as apostas dos jogadores no grupo
        for (Jogador jogador : grupo) {
            List<Aposta> apostas = jogador.getHistoricoApostas();
            for (Aposta aposta : apostas) {
                // Aqui você pode adicionar a lógica para calcular o lucro de cada aposta
                // Considerando as apostas feitas em diferentes rodadas
                lucroTotal += aposta.getResultado(); // Exemplo simplificado
            }
        }
        return lucroTotal;
    }
}
