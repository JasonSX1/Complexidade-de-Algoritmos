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

            System.out.printf("\nResultados da roleta para a rodada %d:\n", rodada);
            System.out.printf("Número: %d - Cor: %s - Tipo: %s\n", numeroSorteado, corSorteada, tipoSorteado);

            for (Jogador jogador : jogadores) {
                double entrada = 10 + random.nextInt(40);
                int numeroApostado = random.nextInt(37);
                double resultado = (numeroApostado == numeroSorteado) ? entrada * 3 : -entrada;

                Aposta aposta = new Aposta(entrada, numeroApostado, numeroSorteado, corSorteada, tipoSorteado,
                        resultado);
                aposta.setNumeroDaRodada(rodada); // Armazena o número da rodada na aposta
                jogador.adicionarAposta(aposta);
            }
        }

        for (Jogador jogador : jogadores) {
            jogador.apostar();
        }

        System.out.println("\nSaldo final de cada jogador:");
        for (Jogador jogador : jogadores) {
            System.out.printf("Jogador apostando: id: %d, Nome: %s, Saldo Inicial: %.2f, Saldo final: %s\n",
                    jogador.getId(), jogador.getNomeCompleto(), jogador.getSaldoInicial(),
                    formatarSaldoFinal(jogador.getSaldo()));

        }

    }

    public static String formatarResultadoAposta(Aposta aposta, Jogador jogador) {
        String resultadoFormatado = (aposta.getResultado() >= 0 ? VERDE : VERMELHO) +
                String.format("%.2f", aposta.getResultado()) + RESET;
        return String.format("%d        | %.2f   | %d      | %d - %s - %s  | %s     | %.2f",
                aposta.getNumeroDaRodada(), aposta.getEntrada(), aposta.getNumeroApostado(), aposta.getNumeroRoleta(),
                aposta.getCorRoleta(), aposta.getParidadeRoleta(), resultadoFormatado, jogador.getSaldo());
    }

    public static String formatarSaldoFinal(double saldoFinal) {
        String corFormatacao = saldoFinal < 0 ? VERMELHO : VERDE;
        return corFormatacao + String.format("%.2f", saldoFinal) + RESET;
    }
}
