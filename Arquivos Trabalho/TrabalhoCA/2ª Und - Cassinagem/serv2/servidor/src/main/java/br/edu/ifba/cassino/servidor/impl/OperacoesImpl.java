// Implementação das operações do Servidor
package br.edu.ifba.cassino.servidor.impl;

import br.edu.ifba.cassino.servidor.modelo.Jogador;
import br.edu.ifba.cassino.servidor.modelo.Aposta;
import br.edu.ifba.cassino.servidor.operacoes.Operacoes;

import java.util.*;
import java.util.stream.Collectors;

public class OperacoesImpl implements Operacoes {
    private final List<Jogador> jogadores = new ArrayList<>(); // Certifica que a lista sempre existe

    @Override
    public void processarJogadores(List<Jogador> novosJogadores) {
        if (novosJogadores == null) {
            System.err.println("[ERRO] Lista de jogadores recebida é nula!");
            return;
        }
        jogadores.addAll(novosJogadores);
        System.out.println("[SERVIDOR] Jogadores processados com sucesso.");
    }

    @Override
    public List<Jogador> getMelhoresJogadores() {
        return jogadores.stream()
                .sorted(Comparator.comparingDouble(Jogador::getSaldo).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }
}
