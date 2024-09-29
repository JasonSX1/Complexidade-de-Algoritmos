package br.edu.ifba.subsetsum.impl;

import java.util.Random;

import br.edu.ifba.subsetsum.estrategia.Estrategia;

public class EstrategiaImpl implements Estrategia<Integer> {

    @Override
    public Integer[] gerarNumeros(int total) {
        Integer[] numeros = new Integer[total];

        Random randomizador = new Random();
        for (int i = 0; i < total; i++) {
            numeros[i] = randomizador.nextInt(Integer.MAX_VALUE);
        }
        return numeros;
    }

    @Override
    public boolean encontrar(Integer[]numeros, int valor) {
        return encontrar(numeros, valor, 0);
    }

    public boolean encontrar(Integer[] numeros, Integer valor, Integer i) {

        if(valor ==0){
            return true;
        }

        if(i == numeros.length){
            return false;
        }

        return encontrar(numeros, valor - numeros[i], i + 1) || encontrar(numeros, valor, i + 1);
    }
}