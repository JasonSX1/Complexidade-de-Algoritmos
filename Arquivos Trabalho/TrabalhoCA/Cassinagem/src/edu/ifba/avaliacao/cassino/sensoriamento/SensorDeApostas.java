package edu.ifba.avaliacao.cassino.sensoriamento;

import edu.ifba.avaliacao.cassino.impl.Aposta;
import edu.ifba.avaliacao.cassino.impl.Jogador;
import edu.ifba.avaliacao.cassino.impl.Roleta;
import java.util.List;
import java.util.Random;

public class SensorDeApostas {

    private static final String VERDE = "\u001B[32m";
    private static final String VERMELHO = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    public static void gerarApostasParaJogadores(List<Jogador> jogadores, int rodadas) {
        Random random = new Random();
        Roleta roleta = new Roleta();

        for (int rodada = 1; rodada <= rodadas; rodada++) {
            roleta.girar();
            int numeroSorteado = roleta.getNumeroSorteado();
            String corSorteada = roleta.getCorSorteada();
            String tipoSorteado = roleta.getTipoSorteado();

            System.out.printf("Resultados da roleta para a rodada %d:\n", rodada);
            System.out.printf("Número: %d - Cor: %s - Tipo: %s\n\n", numeroSorteado, corSorteada, tipoSorteado);

            for (Jogador jogador : jogadores) {
                double entrada = 10 + random.nextInt(40); 
                int numeroApostado = random.nextInt(37);
                double resultado = (numeroApostado == numeroSorteado) ? entrada * 3 : -entrada;

                Aposta aposta = new Aposta(entrada, numeroApostado, numeroSorteado, corSorteada, tipoSorteado, resultado);
                jogador.adicionarAposta(aposta);
            }
        }

        for (Jogador jogador : jogadores) {
            jogador.apostar();
        }
    }

    public static String formatarResultadoAposta(Aposta aposta, Jogador jogador) {
        String resultadoFormatado = (aposta.getResultado() >= 0 ? VERDE : VERMELHO) +
                String.format("%.2f", aposta.getResultado()) + RESET;
        return String.format("%.2f\t| %d\t| %d - %s - %s\t| %s",
                aposta.getEntrada(), aposta.getNumeroApostado(), aposta.getNumeroRoleta(),
                aposta.getCorRoleta(), aposta.getParidadeRoleta(), resultadoFormatado);
    }

    public static String formatarSaldoFinal(double saldoFinal, double saldoInicial) {
        return (saldoFinal >= saldoInicial ? VERDE : VERMELHO) + String.format("%.2f", saldoFinal) + RESET;
    }
}
