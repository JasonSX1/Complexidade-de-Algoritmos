package br.edu.ifba.busca.impl;

import br.edu.ifba.busca.buscadores.Buscador;
import br.edu.ifba.busca.buscadores.ResultadoBusca;

public class BuscadorImpl implements Buscador{ 

    @Override
    public ResultadoBusca buscar(int numero, int[] numeros) {
        ResultadoBusca resultado = new ResultadoBusca();

        for (int i=0; i<numeros.length; i++){
            resultado.setPassosExecutados(i + 1);
            if (numeros[i] == numero){
                resultado.setEncontrado(true);
                break;
            }
        }

        return resultado;
    }
    
}
