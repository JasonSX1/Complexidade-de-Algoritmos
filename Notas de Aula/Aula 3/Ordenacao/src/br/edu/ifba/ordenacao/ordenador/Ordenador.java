package br.edu.ifba.ordenacao.ordenador;

public interface Ordenador<Tipo> {
    
    public String getNomeAlgoritmo();

    public void ordenar(Tipo[] vetor);
}
