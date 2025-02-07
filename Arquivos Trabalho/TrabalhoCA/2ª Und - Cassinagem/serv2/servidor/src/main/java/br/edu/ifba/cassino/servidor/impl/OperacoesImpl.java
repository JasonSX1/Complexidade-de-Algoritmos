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

        // Encontrar os 3 jogadores com maior saldo
        List<Jogador> melhores = new ArrayList<>();
        for (Jogador jogador : jogadoresRegistrados) {
            if (melhores.size() < 3) {
                melhores.add(jogador);
                melhores.sort((j1, j2) -> Double.compare(j2.getSaldo(), j1.getSaldo())); // Ordenação decrescente
            } else {
                // Se o jogador tem saldo maior que o menor do top 3, substitui
                if (jogador.getSaldo() > melhores.get(2).getSaldo()) {
                    melhores.set(2, jogador);
                    melhores.sort((j1, j2) -> Double.compare(j2.getSaldo(), j1.getSaldo())); // Ordenação decrescente
                }
            }
        }

        System.out.println("[SERVIDOR] Melhores jogadores:");
        for (Jogador j : melhores) {
            System.out.println(j);
        }

        return melhores;
    }
}
