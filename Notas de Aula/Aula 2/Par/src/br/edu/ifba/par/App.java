package br.edu.ifba.par;

public class App {

    // Complexidade constante O(1)
    public static boolean isPar(int numero){
        boolean par = (numero % 2 ==0);
        return par;
    }
    public static void main(String[] args) throws Exception {
        int[] numeros = new int[] {1, 2, 3, 4, 10, 15, 20};

        for(int numero: numeros){
            if(isPar(numero)){
                System.out.println("O numero " + numero + " é par");
            } else {
                System.out.println("O numero " + numero + " é ímpar");
            }
        }
    }
}
