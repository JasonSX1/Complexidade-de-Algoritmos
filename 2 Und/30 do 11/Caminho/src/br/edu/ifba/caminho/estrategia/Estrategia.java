package br.edu.ifba.caminho.estrategia;

public interface Estrategia<Origem, Mapa, Distancias>{
    
    public Distancias encontrarMelhorCaminho(Mapa mapa, Origem origem);
}
