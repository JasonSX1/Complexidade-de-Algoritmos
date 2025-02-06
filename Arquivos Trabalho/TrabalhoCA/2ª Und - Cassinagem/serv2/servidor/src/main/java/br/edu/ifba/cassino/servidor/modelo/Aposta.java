// Classe que representa uma Aposta no sistema
package br.edu.ifba.cassino.servidor.modelo;

public class Aposta {
    private String jogador;
    private double valor;
    private String tipo;
    private String resultado;

    public Aposta(String jogador, double valor, String tipo, String resultado) {
        this.jogador = jogador;
        this.valor = valor;
        this.tipo = tipo;
        this.resultado = resultado;
    }

    public String getJogador() {
        return jogador;
    }

    public void setJogador(String jogador) {
        this.jogador = jogador;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Aposta{" +
                "jogador='" + jogador + '\'' +
                ", valor=" + valor +
                ", tipo='" + tipo + '\'' +
                ", resultado='" + resultado + '\'' +
                '}';
    }
}
