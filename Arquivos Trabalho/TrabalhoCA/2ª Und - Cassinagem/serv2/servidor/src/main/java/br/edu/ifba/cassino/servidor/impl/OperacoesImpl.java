package br.edu.ifba.cassino.servidor.impl;

import br.edu.ifba.cassino.servidor.modelo.Jogador;
import br.edu.ifba.cassino.servidor.operacoes.Operacoes;

import java.util.*;
import java.util.stream.Collectors;

public class OperacoesImpl implements Operacoes {
    // Lista de jogadores armazenados globalmente
    private static final List<Jogador> jogadores = new ArrayList<>();

    @Override
    public void processarJogadores(List<Jogador> novosJogadores) {
        if (novosJogadores == null || novosJogadores.isEmpty()) {
            System.err.println("[ERRO] Nenhum jogador foi recebido.");
            return;
        }
        System.out.println("[DEBUG] Armazenando jogadores: " + novosJogadores);
        jogadores.addAll(novosJogadores); // Adiciona novos jogadores sem sobrescrever a lista
    }

    @Override
    public List<Jogador> getMelhoresJogadores() {
        if (jogadores.isEmpty()) {
            System.err.println("[ALERTA] Nenhum jogador armazenado ainda.");
            return Collections.emptyList();
        }
        System.out.println("[DEBUG] Obtendo os 3 melhores jogadores de " + jogadores.size());
        return jogadores.stream()
                .sorted(Comparator.comparingDouble(Jogador::getSaldo).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }
}
