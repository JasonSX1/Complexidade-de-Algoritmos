package edu.ifba.avaliacao.cassino.roleta;

public interface Roleta {

    // Gera o resultado da roleta (número, cor, ímpar/par)
    public ResultadoRoleta sortear();

    // Calcula o retorno financeiro da aposta do jogador com base no resultado da roleta
    public double calcularRetorno(Aposta aposta, ResultadoRoleta resultado);
}