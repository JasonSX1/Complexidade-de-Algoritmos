package br.edu.ifba.busca;

import br.edu.ifba.busca.impl.BuscadorImpl;
import br.edu.ifba.busca.impl.CasosImpl;
import br.edu.ifba.busca.impl.NumerosImpl;
import br.edu.ifba.busca.numeros.Numeros;
import br.edu.ifba.busca.buscadores.Buscador;
import br.edu.ifba.busca.buscadores.ResultadoBusca;
import br.edu.ifba.busca.casos.Casos;

public class App {
    private static final int TOTAL_DE_NUMEROS = 100;
    private static final int NUMERO_PROCURADO = 1;

    public static void main(String[] args) {
        Numeros numeros = new NumerosImpl();
        Buscador buscador = new BuscadorImpl();
        Casos casos = new CasosImpl(TOTAL_DE_NUMEROS / 4);

        ResultadoBusca resultado = buscador.buscar(NUMERO_PROCURADO, numeros.gerar(TOTAL_DE_NUMEROS));

        casos.setResultadoBusca(resultado);
        if (casos.detectarMelhorCaso()) {
            System.out.println("Melhor caso detectado, posição: " + casos.getResultadoBusca().getPassosExecutados());
        } else if (casos.detectarCasoMedio()) {
            System.out.println("Caso médio detectado, posição: " + casos.getResultadoBusca().getPassosExecutados());
        } else if (casos.detectarPiorCaso()) {
            System.out.println("Pior caso detecado.");
        }
    }
}
