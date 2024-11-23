package br.edu.ifba.pacientes.servidor.modelo;

public class Paciente implements Comparable<Paciente> {

    private String identificacao = "";
    private String nome = "";

    public Paciente(String identificacao, String nome) {
        this.identificacao = identificacao;
        this.nome = nome;
    }
    
    public String getIdentificacao() {
        return identificacao;
    }

    public String getNome() {
        return nome;
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
