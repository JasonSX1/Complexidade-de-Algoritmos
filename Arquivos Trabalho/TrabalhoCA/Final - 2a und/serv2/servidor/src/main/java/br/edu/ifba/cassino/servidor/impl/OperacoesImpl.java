package br.edu.ifba.cassino.servidor.impl;

import br.edu.ifba.cassino.servidor.modelo.Jogador;
import br.edu.ifba.cassino.servidor.operacoes.Operacoes;
import java.util.ArrayList;
import java.util.List;

public class OperacoesImpl implements Operacoes {
    private final List<Jogador> jogadoresRegistrados = new ArrayList<>();

    // * Processa e registra os jogadores recebidos.
    // * Complexidade: O(n), onde n é o número de jogadores na lista.
    @Override
    public void processarJogadores(List<Jogador> jogadores) {
        if (jogadores == null || jogadores.isEmpty()) {
            System.err.println("[ERRO] Lista de jogadores recebida é nula ou vazia.");
            return;
        }

        System.out.println("[SERVIDOR] Processando jogadores...");
        for (Jogador jogador : jogadores) { // O(n)
            jogadoresRegistrados.add(jogador); // O(1) para cada inserção na lista
        }
    }

    /**
     * Obtém os 3 jogadores com maior lucro.
     * Complexidade: O(n), onde n é o número total de jogadores registrados.
     */
    @Override
    public List<Jogador> getMelhoresJogadores() {
        if (jogadoresRegistrados.isEmpty()) {
            System.out.println("[SERVIDOR] Nenhum jogador registrado.");
            return new ArrayList<>();
        }

        // Encontrar os 3 jogadores com maior lucro sem ordenar toda a lista
        List<Jogador> melhores = new ArrayList<>();
        double menorLucro = Double.NEGATIVE_INFINITY;

        for (Jogador jogador : jogadoresRegistrados) { // O(n)
            if (melhores.size() < 3) {
                melhores.add(jogador); // O(1)
                menorLucro = Math.min(menorLucro, jogador.getSaldo() - jogador.getSaldoInicial());
            } else {
                for (int i = 0; i < 3; i++) { // O(3), que é O(1)
                    double lucroAtual = jogador.getSaldo() - jogador.getSaldoInicial();
                    if (lucroAtual > (melhores.get(i).getSaldo() - melhores.get(i).getSaldoInicial())) {
                        // Substitui o jogador com menor lucro no top 3
                        melhores.set(i, jogador); // O(1)
                        break;
                    }
                }
            }
        }

        // Verifica se há menos de três jogadores qualificados
        if (melhores.size() < 3) {
            System.out.println("[SERVIDOR] Menos de três jogadores qualificados.");
        }

        // Exibir os melhores jogadores
        System.out.println("===============================================");
        System.out.println(" Dados dos Melhores Jogadores ");
        System.out.println("===============================================");
        System.out.println(" ID |    Nome Completo     | Saldo Inicial |  Saldo Final |   Lucro  ");
        System.out.println("----|----------------------|---------------|--------------|----------");

        for (Jogador jogador : melhores) { // O(3), que é O(1)
            double lucroJogador = jogador.getSaldo() - jogador.getSaldoInicial();
            System.out.printf(" %2d | %-20s | %13.2f | %13.2f | %8.2f\n",
                    jogador.getId(), jogador.getNomeCompleto(),
                    jogador.getSaldoInicial(), jogador.getSaldo(), lucroJogador);
        }

        System.out.println("===============================================");

        return melhores; // O(1)
    }
}
