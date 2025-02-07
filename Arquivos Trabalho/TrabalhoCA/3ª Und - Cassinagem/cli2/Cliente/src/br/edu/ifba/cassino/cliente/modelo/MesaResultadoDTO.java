package br.edu.ifba.cassino.cliente.modelo;

import java.util.List;

public class MesaResultadoDTO {
    private String mesaId;
    private double lucroTotalMesa;
    private double totalApostado;
    private double totalPago;
    private List<Jogador> melhoresJogadores;

    public MesaResultadoDTO() {} // Construtor vazio para serialização JSON

    public MesaResultadoDTO(String mesaId, double lucroTotalMesa, double totalApostado, double totalPago, List<Jogador> melhoresJogadores) {
        this.mesaId = mesaId;
        this.lucroTotalMesa = lucroTotalMesa;
        this.totalApostado = totalApostado;
        this.totalPago = totalPago;
        this.melhoresJogadores = melhoresJogadores;
    }

    // 🔹 Getters e Setters
    public String getMesaId() { return mesaId; }
    public void setMesaId(String mesaId) { this.mesaId = mesaId; }

    public double getLucroTotalMesa() { return lucroTotalMesa; }
    public void setLucroTotalMesa(double lucroTotalMesa) { this.lucroTotalMesa = lucroTotalMesa; }

    public double getTotalApostado() { return totalApostado; }
    public void setTotalApostado(double totalApostado) { this.totalApostado = totalApostado; }

    public double getTotalPago() { return totalPago; }
    public void setTotalPago(double totalPago) { this.totalPago = totalPago; }

    public List<Jogador> getMelhoresJogadores() { return melhoresJogadores; }
    public void setMelhoresJogadores(List<Jogador> melhoresJogadores) { this.melhoresJogadores = melhoresJogadores; }
}
