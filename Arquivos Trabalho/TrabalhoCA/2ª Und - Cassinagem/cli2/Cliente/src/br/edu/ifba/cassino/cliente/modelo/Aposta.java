package br.edu.ifba.cassino.cliente.modelo;

public class Aposta {
    private double entrada;
    private int numeroApostado;
    private int numeroRoleta;
    private String corApostada;
    private String paridadeApostada;

    // A complexidade geral da classe é O(1), tendo em vista que nenhum dos métodos
    // dela como os Getters e Setters possuem laços de repetição ou algo que
    // incremente sua complexidade

    public String getCorApostada() {
        return corApostada;
    }

    public void setCorApostada(String corApostada) {
        this.corApostada = corApostada;
    }

    public String getParidadeApostada() {
        return paridadeApostada;
    }

    public void setParidadeApostada(String paridadeApostada) {
        this.paridadeApostada = paridadeApostada;
    }

    private String corRoleta;
    private String paridadeRoleta;
    private double resultado;
    private int numeroDaRodada;
    private String tipoAposta;

    public Aposta(double entrada, String tipoAposta, int numeroApostado, String corApostada, String paridadeApostada,
            int numeroRoleta, String corRoleta, String paridadeRoleta, double resultado) {
        this.entrada = entrada;
        this.tipoAposta = tipoAposta;
        this.numeroApostado = numeroApostado;
        this.corApostada = corApostada;
        this.paridadeApostada = paridadeApostada;
        this.numeroRoleta = numeroRoleta;
        this.corRoleta = corRoleta;
        this.paridadeRoleta = paridadeRoleta;
        this.resultado = resultado;
    }

    public double getResultado() {
        return resultado;
    }

    public String getTipoAposta() {
        return tipoAposta;
    }

    public int getNumeroRoleta() {
        return numeroRoleta;
    }

    public String getCorRoleta() {
        return corRoleta;
    }

    public String getParidadeRoleta() {
        return paridadeRoleta;
    }

    public double getEntrada() {
        return entrada;
    }

    public int getNumeroApostado() {
        return numeroApostado;
    }

    public int getNumeroDaRodada() {
        return numeroDaRodada;
    }

    public void setNumeroDaRodada(int numeroDaRodada) {
        this.numeroDaRodada = numeroDaRodada;
    }
}
