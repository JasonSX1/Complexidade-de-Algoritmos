package br.edu.ifba.busca.impl;

import br.edu.ifba.busca.buscadores.Buscador;
import br.edu.ifba.busca.buscadores.ResultadoBusca;

public class BuscadorBinarioImpl implements Buscador {

    @Override
    public ResultadoBusca buscar(int numero, int[] numeros) {
        ResultadoBusca resultado = new ResultadoBusca();
        
        int passosExecutados = 0;
        int fim = numeros.length - 1;
        if(numero< numeros[fim]){
            int inicio = 0;
            int meio = 0;
            while (inicio <=fim){
                passosExecutados++;
                
                meio = (inicio + fim) / 2;
                if (numero > numeros[meio]){
                    inicio = meio + 1;
                } else if (numero < numeros[meio]){
                    fim = meio - 1;
                } else {
                    resultado.setEncontrado(true);
                    break;
                }
            }
        }
        resultado.setPassosExecutados(passosExecutados);

        return resultado;
    }
}