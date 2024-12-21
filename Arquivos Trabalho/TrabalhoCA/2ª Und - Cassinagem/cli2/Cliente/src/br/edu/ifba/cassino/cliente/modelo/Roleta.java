package br.edu.ifba.cassino.cliente.modelo;

import java.util.Random;

public class Roleta {

    //Complexidade geral da classe - O(1)

    private int numeroSorteado;
    private String corSorteada; //Vermelho/Preto/Verde
    private String tipoSorteado; // Ímpar/Par

    private static final int[] NUMEROS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
                                          19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36};

    private static final String[] CORES = {"VERDE", "VERDE", "VERMELHO", "PRETO", "VERMELHO", "PRETO", "VERMELHO", 
                                           "PRETO", "VERMELHO", "PRETO", "PRETO", "VERMELHO", "PRETO", "VERMELHO", 
                                           "PRETO", "VERMELHO", "PRETO", "VERMELHO", "VERMELHO", "PRETO", "VERMELHO", 
                                           "PRETO", "VERMELHO", "PRETO", "VERMELHO", "PRETO", "VERMELHO", "PRETO", 
                                           "VERMELHO", "PRETO", "PRETO", "VERMELHO", "PRETO", "VERMELHO", "PRETO", 
                                           "VERMELHO", "PRETO"};

    public Roleta() {
        girar(); // Inicializa com um giro ao criar a roleta
    }

    public int getNumeroSorteado() {
        return numeroSorteado;
    }

    public String getCorSorteada() {
        return corSorteada;
    }

    public String getTipoSorteado() {
        return tipoSorteado;
    }

    // Gira a roleta e define o número, a cor e o tipo (par/ímpar)
    public void girar() { 
        Random random = new Random();
        int indice = random.nextInt(NUMEROS.length);
        numeroSorteado = NUMEROS[indice];
        corSorteada = CORES[indice];
        tipoSorteado = (numeroSorteado != 0 && numeroSorteado % 2 == 0) ? "PAR" : "ÍMPAR";
    }
}
