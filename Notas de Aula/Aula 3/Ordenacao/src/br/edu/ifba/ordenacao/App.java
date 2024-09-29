package br.edu.ifba.ordenacao;

import br.edu.ifba.ordenacao.ordenador.Ordenador;
import br.edu.ifba.ordenacao.impl.*;

public class App {
    public static void main(String[] args) throws Exception {
        Integer[] numeros = new Integer[] { 2, 100, 4, 20, 13, 33, 22, 17};

        Ordenador<Integer> ordenador = new Bubble();
        ordenador.ordenar(numeros);

        for (Integer numero : numeros) {
            System.out.println(numero);
        }
    }
}
