package br.edu.ifba.avaliacao.servidor.impl;

import java.util.*;

import br.edu.ifba.avaliacao.servidor.modelo.Jogador;
import br.edu.ifba.avaliacao.servidor.operacoes.Operacoes;

public class OperacoesImpl implements Operacoes {
    private Map<Integer, Jogador> jogadores = new TreeMap<>();

    @Override
    public void gravarDadosJogador(Jogador jogador) {
        jogadores.put(jogador.getId(), jogador);
    }

    @Override
    public void registrarMelhorGrupo(List<Jogador> grupo) {
        System.out.println("Melhor grupo recebido: " + grupo);
    }

    @Override
    public List<Jogador> listarJogadores() {
        return new ArrayList<>(jogadores.values());
    }
}
