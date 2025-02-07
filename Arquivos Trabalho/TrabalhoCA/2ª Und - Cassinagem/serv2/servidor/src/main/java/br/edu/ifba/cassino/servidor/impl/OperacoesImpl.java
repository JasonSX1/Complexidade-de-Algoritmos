package br.edu.ifba.cassino.servidor.impl;

import br.edu.ifba.cassino.servidor.modelo.Jogador;
import br.edu.ifba.cassino.servidor.operacoes.Operacoes;
import java.util.ArrayList;
import java.util.List;

public class OperacoesImpl implements Operacoes {
    private final List<Jogador> jogadoresRegistrados = new ArrayList<>();

    @Override
    public void processarJogadores(List<Jogador> jogadores) {
        if (jogadores == null || jogadores.isEmpty()) {
            System.err.println("[ERRO] Lista de jogadores recebida é nula ou vazia.");
            return;
        }

        System.out.println("[SERVIDOR] Processando jogadores...");
        for (Jogador jogador : jogadores) {
            System.out.println("[SERVIDOR] Registrando jogador: " + jogador);
            jogadoresRegistrados.add(jogador);
        }
    }

    @Override
    public List<Jogador> getMelhoresJogadores() {
        if (jogadoresRegistrados.isEmpty()) {
            System.out.println("[SERVIDOR] Nenhum jogador registrado.");
            return new ArrayList<>();
        }

        // Encontrar os 3 jogadores com maior saldo sem usar sort()
        List<Jogador> melhores = new ArrayList<>();
        double menorSaldo = Double.NEGATIVE_INFINITY;

        for (Jogador jogador : jogadoresRegistrados) {
            if (melhores.size() < 3) {
                melhores.add(jogador);
                menorSaldo = Math.min(menorSaldo, jogador.getSaldo());
            } else {
                for (int i = 0; i < 3; i++) {
                    if (jogador.getSaldo() > melhores.get(i).getSaldo()) {
                        // Substitui o jogador com menor saldo do top 3
                        melhores.set(i, jogador);
                        break;
                    }
                }
            }
        }

        // Verifica se de fato pegamos três jogadores
        if (melhores.size() < 3) {
            System.out.println("[SERVIDOR] Menos de três jogadores qualificados.");
        }

        // Exibir os jogadores armazenados
        System.out.println("===============================================");
        System.out.println(" Dados dos Melhores Jogadores ");
        System.out.println("===============================================");
        System.out.println(" ID |    Nome     | Saldo Inicial |  Saldo Final");
        System.out.println("----|-------------|---------------|--------------");

        for (Jogador jogador : melhores) {
            System.out.printf(" %2d | %-12s | %13.2f | %13.2f\n",
                    jogador.getId(), jogador.getNome(), jogador.getSaldoInicial(), jogador.getSaldo());
        }

        System.out.println("===============================================");

        return melhores;
    }
}