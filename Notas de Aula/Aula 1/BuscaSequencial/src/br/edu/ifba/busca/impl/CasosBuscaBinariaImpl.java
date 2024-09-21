package br.edu.ifba.busca.impl;

import br.edu.ifba.busca.casos.Casos;

public class CasosBuscaBinariaImpl extends Casos {

    private int limiarMelhorCaso = 0;

    public CasosBuscaBinariaImpl(int limiarMelhorCaso) {
        this.limiarMelhorCaso = limiarMelhorCaso;
    }

    @Override
    public boolean detectarMelhorCaso() {
        boolean detectado = false;

        if (resultadoBusca.isEncontrado() && resultadoBusca.getPassosExecutados() <= limiarMelhorCaso) {
            detectado = true;
        }
        return detectado;
    }

    @Override
    public boolean detectarCasoMedio() {
        boolean detectado = false;

        if (resultadoBusca.isEncontrado() && resultadoBusca.getPassosExecutados() > limiarMelhorCaso) {
            detectado = true;
        }
        return detectado;
    }

    @Override
    public boolean detectarPiorCaso() {
        boolean detecado = true;

        if(resultadoBusca.isEncontrado()){
            detecado = false;
        }
        return detecado;
    }
    
}
