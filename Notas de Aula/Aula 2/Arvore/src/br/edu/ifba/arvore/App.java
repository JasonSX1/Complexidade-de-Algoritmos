package br.edu.ifba.arvore;

import br.edu.ifba.arvore.estrutura.Arvore;
import br.edu.ifba.arvore.impl.ArvoreImpl;

public class App {
    public static void main(String[] args) throws Exception {
        int[] numeros = new int[]{ 50, 30, 20, 40, 70, 60, 80 };
        
        Arvore<Integer> arvore = new ArvoreImpl();
        
        for (int numero : numeros) {
            arvore.inserir(numero);
        }

        arvore.imprimir();

        arvore.buscar(80);

        System.out.println("Passos de busca: " + arvore.getPassosDeBusca());

    }
}
