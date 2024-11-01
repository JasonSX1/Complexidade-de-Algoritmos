package edu.ifba.avaliacao.cassino.impl;

import edu.ifba.avaliacao.cassino.ordenador.OrdenadorDeApostas;
import edu.ifba.avaliacao.cassino.impl.Aposta;

import java.util.List;
import java.util.ArrayList;

public class OrdenadorImpl implements OrdenadorDeApostas {

    @Override
    public List<Aposta> ordenarPorLucro(List<Aposta> apostas) {
        if (apostas.size() <= 1) {
            return apostas; // Se a lista tiver um ou zero elementos, já está ordenada (complexidade O(1)).
        }

        // Divide a lista ao meio
        int meio = apostas.size() / 2;
        List<Aposta> esquerda = new ArrayList<>(apostas.subList(0, meio));
        List<Aposta> direita = new ArrayList<>(apostas.subList(meio, apostas.size()));

        // Ordena cada metade recursivamente
        esquerda = ordenarPorLucro(esquerda); // Recursão em metade da lista (complexidade O(log n)).
        direita = ordenarPorLucro(direita); // Recursão em metade da lista (complexidade O(log n)).

        // Mescla as listas ordenadas
        return merge(esquerda, direita);
    }

    // Função para mesclar duas listas ordenadas em uma única lista ordenada
    private List<Aposta> merge(List<Aposta> esquerda, List<Aposta> direita) {
        List<Aposta> resultado = new ArrayList<>();
        int i = 0, j = 0;

        // Comparação e mesclagem das sublistas até que uma delas esteja completamente processada
        while (i < esquerda.size() && j < direita.size()) {
            if (esquerda.get(i).getResultado() <= direita.get(j).getResultado()) {
                resultado.add(esquerda.get(i));
                i++;
            } else {
                resultado.add(direita.get(j));
                j++;
            }
        }

        // Adiciona os elementos restantes da lista esquerda (se houver)
        while (i < esquerda.size()) {
            resultado.add(esquerda.get(i));
            i++;
        }

        // Adiciona os elementos restantes da lista direita (se houver)
        while (j < direita.size()) {
            resultado.add(direita.get(j));
            j++;
        }

        return resultado;
    }
}
