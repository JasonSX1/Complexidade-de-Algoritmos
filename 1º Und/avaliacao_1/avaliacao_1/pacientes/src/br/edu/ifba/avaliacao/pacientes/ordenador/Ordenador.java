package br.edu.ifba.avaliacao.pacientes.ordenador;

import java.util.List;

public abstract class Ordenador<TipoDado> {
    
    protected List<TipoDado> leituras = null;

    public Ordenador(List<TipoDado> leituras) {
        this.leituras = leituras;
    }

    public List<TipoDado> getLeituras() {
        return leituras;
    }
    
    public abstract void ordenar();

}
