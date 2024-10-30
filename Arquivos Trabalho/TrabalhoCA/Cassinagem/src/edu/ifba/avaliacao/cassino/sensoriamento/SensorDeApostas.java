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

    // Variáveis estáticas para configurar os parâmetros
    public static int APOSTA_MINIMA = 10; // Valor mínimo da aposta
    public static int APOSTA_MAXIMA = 50; // Valor máximo da aposta
    public static int SALDO_INICIAL_MIN = 100; // Saldo inicial mínimo para jogadores
    public static int SALDO_INICIAL_MAX = 1000; // Saldo inicial máximo para jogadores

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
                int entrada = APOSTA_MINIMA + random.nextInt(APOSTA_MAXIMA - APOSTA_MINIMA + 1);

                // Escolhe aleatoriamente o tipo de aposta
                String tipoAposta = escolherTipoAposta(random);
                int numeroApostado = -1;
                String corApostada = null;
                String paridadeApostada = null;
                int resultado = -entrada;

                switch (tipoAposta) {
                    case "NUMERO":
                        numeroApostado = random.nextInt(37);
                        resultado = (numeroApostado == numeroSorteado) ? entrada * 3 : -entrada;
                        break;

                    case "COR":
                        corApostada = random.nextBoolean() ? "VERMELHO" : "PRETO";
                        resultado = (corApostada.equals(corSorteada)) ? (int) (entrada * 1.5) : -entrada;
                        break;

                    case "PARIDADE":
                        paridadeApostada = random.nextBoolean() ? "PAR" : "ÍMPAR";
                        resultado = (paridadeApostada.equals(tipoSorteado)) ? (int) (entrada * 1.5) : -entrada;
                        break;
                }

                Aposta aposta = new Aposta(entrada, tipoAposta, numeroApostado, corApostada, paridadeApostada,
                        numeroSorteado, corSorteada, tipoSorteado, resultado);
                aposta.setNumeroDaRodada(rodada);
                jogador.adicionarAposta(aposta);
            }
        }

        for (Jogador jogador : jogadores) {
            jogador.apostar();
        }

        System.out.println("\nSaldo final de cada jogador:");
        for (Jogador jogador : jogadores) {
            System.out.printf("Jogador apostando: id: %d, Nome: %s, Saldo Inicial: %d, Saldo final: %s\n",
                    jogador.getId(), jogador.getNomeCompleto(), jogador.getSaldoInicial(),
                    formatarSaldoFinal(jogador.getSaldo(), jogador.getSaldoInicial()));
        }
    }

    // Método auxiliar para escolher o tipo de aposta
    private static String escolherTipoAposta(Random random) {
        int tipo = random.nextInt(3);
        switch (tipo) {
            case 0:
                return "NUMERO";
            case 1:
                return "COR";
            case 2:
                return "PARIDADE";
            default:
                return "NUMERO";
        }
    }

    public static String formatarResultadoAposta(Aposta aposta, int saldoAtual) {
        // Formata a entrada (valor apostado em reais) e o detalhe da aposta
        String entradaFormatada = String.format("%7.2f", aposta.getEntrada());

        // Formatação da coluna de detalhes da aposta, exibindo o número, paridade ou
        // cor conforme o tipo
        String apostaDetalheFormatada = switch (aposta.getTipoAposta()) {
            case "NUMERO" -> String.format("%02d", aposta.getNumeroApostado());
            case "PARIDADE" -> aposta.getParidadeApostada();
            case "COR" -> aposta.getCorApostada();
            default -> "";
        };

        // Formata a linha de saída com alinhamento consistente
        return String.format(
                "%-4d    | %-8s | %7s | %-8s | %02d - %-8s - %-7s | %9.2f | %14d",
                aposta.getNumeroDaRodada(),
                entradaFormatada,
                apostaDetalheFormatada,
                aposta.getNumeroRoleta(),
                aposta.getCorRoleta(),
                aposta.getParidadeRoleta(),
                aposta.getResultado(),
                saldoAtual);
    }

    public static String formatarSaldoFinal(int saldoFinal, int saldoInicial) {
        return (saldoFinal >= saldoInicial ? VERDE : VERMELHO) + String.format("%d", saldoFinal) + RESET;
    }
}