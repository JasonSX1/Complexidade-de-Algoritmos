package br.edu.ifba.pacientes.servidor.operacoes;

import java.util.List;

import br.edu.ifba.pacientes.servidor.modelo.Jogador;

public interface Operacoes {
    void gravarDadosJogador(Jogador jogador);
    void registrarMelhorGrupo(List<Jogador> grupo);
    List<Jogador> listarJogadores();
}
