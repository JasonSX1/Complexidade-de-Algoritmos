package br.edu.ifba.subsetsum;

import br.edu.ifba.subsetsum.estrategia.Estrategia;
import br.edu.ifba.subsetsum.impl.EstrategiaImpl;

public class App {
    public static void main(String[] args) throws Exception {
        Estrategia<Integer> estrategia = new EstrategiaImpl();
        Integer[] numeros = estrategia.gerarNumeros(1000);

        if (estrategia.encontrar(numeros, 25)){
            System.out.println("Existe um subconjunto cuja soma é igual ao número desejado. ");
        } else {
            System.out.println("Não existe um subconjunto cuja soma é igual ao número desejado. ");
        }
    }
}
