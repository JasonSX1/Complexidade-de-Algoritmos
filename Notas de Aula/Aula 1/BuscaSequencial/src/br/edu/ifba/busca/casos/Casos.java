package br.edu.ifba.busca.casos;

import br.edu.ifba.busca.buscadores.ResultadoBusca;

public abstract class Casos {
    
    protected ResultadoBusca resultadoBusca = null;

    public ResultadoBusca getResultadoBusca() {
        return resultadoBusca;
    }

    public void setResultadoBusca(ResultadoBusca resultadoBusca) {
        this.resultadoBusca = resultadoBusca;
    }

    public abstract boolean detectarMelhorCaso();

    public abstract boolean detectarCasoMedio();

    public abstract boolean detectarPiorCaso();
}
