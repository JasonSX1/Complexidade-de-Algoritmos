package br.edu.ifba.cassino.cliente.modelo;

import java.util.List;

// Complexidade geral da classe é O(1) para a maioria dos métodos, exceto para o método getMelhoresJogadores, que é O(n), já que depende do tamanho da lista que recebe.

public class MesaResultadoDTO {
    private String mesaId;
    private double saldoFinalMesa;
    private List<Jogador> melhoresJogadores;

    public MesaResultadoDTO(String mesaId, double saldoFinalMesa, List<Jogador> melhoresJogadores) {
        this.mesaId = mesaId;
        this.saldoFinalMesa = saldoFinalMesa;
        this.melhoresJogadores = melhoresJogadores;
    }

    public String getMesaId() {
        return mesaId;
    }

    public double getSaldoFinalMesa() {
        return saldoFinalMesa;
    }

    public List<Jogador> getMelhoresJogadores() {
        return melhoresJogadores;
    }
}