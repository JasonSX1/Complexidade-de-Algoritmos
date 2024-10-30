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
    

        System.out.println("\nSaldo final de cada jogador:");
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
        String resultadoFormatado = (aposta.getResultado() >= 0 ? VERDE : VERMELHO) +
                String.format("%.2f", aposta.getResultado()) + RESET;
        return String.format("%d        | %.2f   | %d      | %d - %s - %s  | %s     | %.2f",
                aposta.getNumeroDaRodada(), aposta.getEntrada(), aposta.getNumeroApostado(), aposta.getNumeroRoleta(),
                aposta.getCorRoleta(), aposta.getParidadeRoleta(), resultadoFormatado, saldoResultante);
    }
    

    public static String formatarSaldoFinal(double saldoFinal, double saldoInicial) {
        return (saldoFinal >= saldoInicial ? VERDE : VERMELHO) + String.format("%.2f", saldoFinal) + RESET;
    }
}
