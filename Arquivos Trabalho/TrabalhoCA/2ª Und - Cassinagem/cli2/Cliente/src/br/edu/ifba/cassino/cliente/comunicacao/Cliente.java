package br.edu.ifba.cassino.cliente.comunicacao;

import br.edu.ifba.cassino.cliente.modelo.Jogador;

public interface Cliente {
    void configurar(int totalJogadores, int jogadoresPorLeva);
    void iniciar();
    void enviarDadosMesa();
}
