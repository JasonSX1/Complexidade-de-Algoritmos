package br.edu.ifba.pacientes.cliente.comunicacao;

public interface Comunicacao {
    
    public String enviar(String id, int batimentos, int temperatura) throws Exception;
}
