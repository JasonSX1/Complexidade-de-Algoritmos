package br.edu.ifba.cassino.servidor.modelo;

import java.util.List;

// A complexidade geral desta classe é O(1), pois todos os métodos realizam apenas atribuições ou retornos diretos.

public class JogadoresWrapper {
    private List<Jogador> jogadores;

    public JogadoresWrapper() {}

    public JogadoresWrapper(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
