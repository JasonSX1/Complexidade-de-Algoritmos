package br.edu.ifba.ordenacao.impl;

import br.edu.ifba.ordenacao.ordenador.Ordenador;

public class Bubble implements Ordenador<Integer> {

    @Override
    public String getNomeAlgoritmo() {
        return "Bubble Sort.";
    }

    @Override
    public void ordenar(Integer[] vetor) {
        if (vetor == null || vetor.length == 0) {
            return;
        }

        int n = vetor.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (vetor[j] > vetor[j + 1]) {
                    int temp = vetor[j];
                    vetor[j] = vetor[j + 1];
                    vetor[j + 1] = temp;
                }
            }
        }
    }
}
