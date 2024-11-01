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
    
                // Escolhe aleatoriamente o tipo de aposta
                String tipoAposta = escolherTipoAposta(random);
                int numeroApostado = -1;
                String corApostada = null;
                String paridadeApostada = null;
                double resultado = -entrada;
    
                switch (tipoAposta) {
                    case "NUMERO":
                        numeroApostado = random.nextInt(37);
                        resultado = (numeroApostado == numeroSorteado) ? entrada * 3 : -entrada;
                        break;
    
                    case "COR":
                        corApostada = random.nextBoolean() ? "VERMELHO" : "PRETO";
                        resultado = (corApostada.equals(corSorteada)) ? entrada * 1.5 : -entrada;
                        break;
    
                    case "PARIDADE":
                        paridadeApostada = random.nextBoolean() ? "PAR" : "ÍMPAR";
                        resultado = (paridadeApostada.equals(tipoSorteado)) ? entrada * 1.5 : -entrada;
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
    //FUNÇÃO EXTRA - CRIADA À PARTE, SOMENTE PARA FINS DE DEMONSTRAÇÃO
        System.out.println("\nSaldo final de cada jogador:");
        System.out.println(" ");
        for (Jogador jogador : jogadores) {
            System.out.printf("Jogador apostando: id: %d, Nome: %s, Saldo Inicial: %.2f, Saldo final: %s\n",
                    jogador.getId(), jogador.getNomeCompleto(), jogador.getSaldoInicial(),
                    formatarSaldoFinal(jogador.getSaldo(), jogador.getSaldoInicial()));
        }
    }
    
    // Método auxiliar para escolher o tipo de aposta
    private static String escolherTipoAposta(Random random) {
        int tipo = random.nextInt(3);
        switch (tipo) {
            case 0: return "NUMERO";
            case 1: return "COR";
            case 2: return "PARIDADE";
            default: return "NUMERO";
        }
    }
    

    public static String formatarResultadoAposta(Aposta aposta, double saldoResultante) {
        final String VERDE = "\u001B[32m";
        final String VERMELHO = "\u001B[31m";
        final String RESET = "\u001B[0m";
    
        // Formatação condicional do resultado em cores
        String resultadoFormatado = (aposta.getResultado() >= 0 ? VERDE : VERMELHO) +
                                    String.format("%9.2f", aposta.getResultado()) + RESET;
    
        // Número da rodada com ajuste de alinhamento
        String numeroRodada = aposta.getNumeroDaRodada() < 10 ? " " + aposta.getNumeroDaRodada()
                                                               : String.valueOf(aposta.getNumeroDaRodada());
    
        // Ajuste para exibir zero à esquerda nos números da roleta e da aposta
        String numeroApostadoFormatado = aposta.getNumeroApostado() >= 0
                                         ? String.format("%02d", aposta.getNumeroApostado())
                                         : "--";
        String numeroRoletaFormatado = String.format("%02d", aposta.getNumeroRoleta());
    
        // Formatação do tipo de aposta (exibe apenas o valor, sem o tipo)
        String apostaDetalhe = switch (aposta.getTipoAposta()) {
            case "NUMERO" -> numeroApostadoFormatado;
            case "COR" -> aposta.getCorApostada();
            case "PARIDADE" -> aposta.getParidadeApostada();
            default -> "";
        };
    
        // Ajuste do tamanho do tipo de aposta e valor de aposta para alinhamento
        String tipoApostaFormatado = String.format("%-7s", aposta.getTipoAposta());
        String apostaDetalheFormatado = String.format("%-8s", apostaDetalhe);
    
        // Retorna a linha formatada
        return String.format("%s     | %-8s | %7.2f | %-8s | %02d - %-8s - %-7s | %s | %14.2f",
                             numeroRodada,
                             tipoApostaFormatado,
                             aposta.getEntrada(),
                             apostaDetalheFormatado,
                             aposta.getNumeroRoleta(),
                             aposta.getCorRoleta(),
                             aposta.getParidadeRoleta(),
                             resultadoFormatado,
                             saldoResultante);
    }
    

    public static String formatarSaldoFinal(double saldoFinal, double saldoInicial) {
        return (saldoFinal >= saldoInicial ? VERDE : VERMELHO) + String.format("%.2f", saldoFinal) + RESET;
    }
}
