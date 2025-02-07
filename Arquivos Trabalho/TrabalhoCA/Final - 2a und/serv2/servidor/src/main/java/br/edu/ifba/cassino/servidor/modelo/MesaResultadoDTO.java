package br.edu.ifba.cassino.servidor.modelo;

import java.util.List;

public class MesaResultadoDTO {
    private String mesaId;
    private List<Jogador> melhoresJogadores;
    private double saldoFinalMesa;

    public MesaResultadoDTO() {} // Construtor vazio necessário para desserialização

    public MesaResultadoDTO(String mesaId, List<Jogador> melhoresJogadores, double saldoFinalMesa) {
        this.mesaId = mesaId;
        this.melhoresJogadores = melhoresJogadores;
        this.saldoFinalMesa = saldoFinalMesa;
    }

    public String getMesaId() {
        return mesaId;
    }

    public void setMesaId(String mesaId) {
        this.mesaId = mesaId;
    }

    public List<Jogador> getMelhoresJogadores() {
        return melhoresJogadores;
    }

    public void setMelhoresJogadores(List<Jogador> melhoresJogadores) {
        this.melhoresJogadores = melhoresJogadores;
    }

    public double getSaldoFinalMesa() {
        return saldoFinalMesa;
    }

    public void setSaldoFinalMesa(double saldoFinalMesa) {
        this.saldoFinalMesa = saldoFinalMesa;
    }
}
