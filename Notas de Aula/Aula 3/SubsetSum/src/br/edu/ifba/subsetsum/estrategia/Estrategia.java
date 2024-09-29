package br.edu.ifba.subsetsum.estrategia;

public interface Estrategia<Tipo> {
    
    public Tipo[] gerarNumeros(int total);

    public boolean encontrar(Tipo[] numeros, int valor);
}
