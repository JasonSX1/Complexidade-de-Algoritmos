package br.edu.ifba.cassino.cliente.comunicacao;

public interface Cliente {
    void configurar(int totalJogadores, int jogadoresPorLeva);
    void iniciar();
    void enviarDadosMesa();
}
