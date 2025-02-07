package br.edu.ifba.cassino.servidor.modelo;

import java.util.List;

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
