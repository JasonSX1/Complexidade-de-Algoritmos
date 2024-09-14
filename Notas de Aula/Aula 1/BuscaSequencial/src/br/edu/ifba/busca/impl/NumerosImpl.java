package br.edu.ifba.busca.impl;

import java.util.Random;

import br.edu.ifba.busca.numeros.Numeros;

public class NumerosImpl implements Numeros {

    @Override
    public int[] gerar(int total) {
        int[] vetor = new int[total];

        for (int i=0; i<total; i++){
            vetor[i] = i;
        }


        Random randomizador = new Random();
        for(int i=0; i<total; i++){
            int posicao = randomizador.nextInt(total);
            
            int temp = vetor[i]; 
            vetor[i] = vetor[posicao];
            vetor[posicao] = temp;
        }

        return vetor;
    }


    
}
