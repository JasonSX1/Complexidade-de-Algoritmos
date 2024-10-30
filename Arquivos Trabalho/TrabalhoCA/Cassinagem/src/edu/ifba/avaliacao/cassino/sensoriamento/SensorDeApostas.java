package edu.ifba.avaliacao.cassino.sensoriamento;

import java.util.List;
import java.util.Random;

import edu.ifba.avaliacao.cassino.impl.Aposta;
import edu.ifba.avaliacao.cassino.impl.Jogador;
import edu.ifba.avaliacao.cassino.impl.Roleta;

public class SensorDeApostas {

    public static void gerarApostasParaJogadores(List<Jogador> jogadores, int rodadas) {
        Random random = new Random();
        Roleta roleta = new Roleta();

        // Imprimindo os jogadores monitorados
        System.out.println("Imprimindo os Jogadores...\nLista de jogadores monitorados:");
        for (int i = 0; i < jogadores.size(); i++) {
            Jogador jogador = jogadores.get(i);
            System.out.printf("Jogador apostando: id: %d, Nome: %s, Saldo: %.2f\n",
                    i + 1, jogador.getNomeCompleto(), jogador.getSaldo());
        }

        // Gerar e imprimir os resultados da roleta para cada rodada
        System.out.println("\nResultados da roleta para cada rodada:");
        for (int rodada = 1; rodada <= rodadas; rodada++) {
            // Gira a roleta e captura o resultado
            roleta.girar();
            int numeroSorteado = roleta.getNumeroSorteado();
            String corSorteada = roleta.getCorSorteada();
            String tipoSorteado = roleta.getTipoSorteado();

            // Imprime o resultado da roleta para a rodada atual
            System.out.printf("Resultados da roleta para a rodada %d:\n", rodada);
            System.out.printf("Número: %d - Cor: %s - Tipo: %s\n\n", numeroSorteado, corSorteada, tipoSorteado);

            // Processa as apostas de cada jogador para a rodada atual
            for (Jogador jogador : jogadores) {
                double entrada = 10 + random.nextInt(40); // Valor entre 10 e 50
                int numeroApostado = random.nextInt(37);  // Número entre 0 e 36

                // Calcula o resultado da aposta
                double resultado = (numeroApostado == numeroSorteado) ? entrada * 3 : -entrada;
                jogador.setSaldo(jogador.getSaldo() + resultado); // Atualiza saldo com o resultado da aposta

                // Cria e adiciona a aposta ao histórico do jogador
                Aposta aposta = new Aposta(entrada, numeroApostado, numeroSorteado, corSorteada, tipoSorteado, resultado);
                jogador.adicionarAposta(aposta);
            }
        }

        // Imprime o histórico de apostas de cada jogador
        for (Jogador jogador : jogadores) {
            System.out.printf("\nApostas do %s:\n", jogador.getNomeCompleto());
            System.out.println("RODADA   | ENTRADA  | APOSTA  | RESULTADO DA ROLETA        | RESULTADO   | SALDO RESULTANTE");

            double saldoAtual = jogador.getSaldoInicial(); // Saldo inicial do jogador
            int rodadaAtual = 1;

            for (Aposta aposta : jogador.getHistoricoApostas()) {
                saldoAtual += aposta.getResultado();
                System.out.printf("%-9d| %-8.2f | %-7d | %2d - %-8s - %-4s | %-11.2f | %.2f\n",
                        rodadaAtual++, aposta.getEntrada(), aposta.getNumeroApostado(),
                        aposta.getNumeroRoleta(), aposta.getCorRoleta(),
                        aposta.getParidadeRoleta(), aposta.getResultado(), saldoAtual);
            }
        }
    }
}
