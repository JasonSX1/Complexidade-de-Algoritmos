package br.edu.ifba.avaliacao.servidor.operacoes;

import java.util.List;

import br.edu.ifba.avaliacao.servidor.modelo.Jogador;

public interface Operacoes {
    void gravarDadosJogador(Jogador jogador);
    void registrarMelhorGrupo(List<Jogador> grupo);
    List<Jogador> listarJogadores();
}