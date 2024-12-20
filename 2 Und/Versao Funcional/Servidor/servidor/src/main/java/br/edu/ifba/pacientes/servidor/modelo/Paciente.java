package br.edu.ifba.pacientes.servidor.modelo;

public class Paciente implements Comparable<Paciente> {

    private String identificacao = "";
    private String nome = "";
    private boolean emergencia = false;

    public Paciente(String identificacao, String nome, boolean emergencia) {
        this.identificacao = identificacao;
        this.nome = nome;
        this.emergencia = emergencia;
    }
    
    public String getIdentificacao() {
        return identificacao;
    }

    public String getNome() {
        return nome;
    }

    public boolean emEmergencia() {
        return emergencia;
    }

    @Override
    public String toString() {
        return "id: " + identificacao + ", nome: " + nome;
    }

    @Override
    public int compareTo(Paciente o) {
        return this.identificacao.compareTo(o.getIdentificacao());
    }

}
