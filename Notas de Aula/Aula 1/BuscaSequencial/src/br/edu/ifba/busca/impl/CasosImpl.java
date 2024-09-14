package br.edu.ifba.busca.impl;

import br.edu.ifba.busca.casos.Casos;

public class CasosImpl extends Casos {

    private int limiarMelhorCaso = 0;

    public CasosImpl(int limiarMelhorCaso) {
        this.limiarMelhorCaso = limiarMelhorCaso;
    }

    //está dentro do limiar de posicoes otimas do vetor
    @Override
    public boolean detectarMelhorCaso() {
        boolean detectado = false;
        if(resultadoBusca.isEncontrado() && resultadoBusca.getPassosExecutados() <= limiarMelhorCaso){
            detectado = true;
        }
        return detectado;
    }

    //O numero é encontrado, mas não está dentro do limiar
    @Override
    public boolean detectarCasoMedio() {
        boolean detectado = false;
        if(resultadoBusca.isEncontrado() && resultadoBusca.getPassosExecutados() > limiarMelhorCaso){
            detectado = true;
        }
        return detectado;
    }

    //O pior caso consiste em NÃO estar no vetor
    @Override
    public boolean detectarPiorCaso() {
        boolean detectado = true;
        if(!resultadoBusca.isEncontrado()){
            detectado = false;
        }
        //O_o ANALISE
        return detectado;
    }
    
}
