package src.br.edu.ifba.SomaVetor;

public class App {
    public static void main(String[] args) throws Exception {
        int [] numeros = new int[]{2, 5, 10, 13, 24};
        int SomaVetor = 0;

        for(int numero: numeros){
            SomaVetor += numero;
        }
        System.out.println("A soma dos elementos do vetor é: " + SomaVetor);
    }
}
