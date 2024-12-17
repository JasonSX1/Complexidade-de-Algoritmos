package br.edu.ifba.encriptacao;

import java.security.KeyPair;

import br.edu.ifba.encriptacao.aleatoriedade.GeradorDeAleatoriedadeReal;
import br.edu.ifba.encriptacao.chaves.GeradorDeChaves;
import br.edu.ifba.encriptacao.encriptador.Encriptador;
import br.edu.ifba.encriptacao.excecoes.FalhaEncriptacao;
import br.edu.ifba.encriptacao.excecoes.FalhaGeracaoDeChaves;
import br.edu.ifba.encriptacao.impl.EncriptadorImpl;
import br.edu.ifba.encriptacao.impl.GeradorDeChavesImpl;

public class App {

    private static final String CAMINHO_VIDEO = "encriptation\\src\\main\\java\\br\\edu\\ifba\\encriptacao\\video\\lava.mp4";

    private static final String ALGORITMO_DE_ENCRIPTACAO = "RSA";
    public static void main(String[] args) throws Throwable {
        GeradorDeChaves<GeradorDeAleatoriedadeReal> geradorDeChaves = new GeradorDeChavesImpl();
        GeradorDeAleatoriedadeReal geradorDeAleatoriedade = new GeradorDeAleatoriedadeReal(CAMINHO_VIDEO);
        geradorDeChaves.inicializar(geradorDeAleatoriedade, ALGORITMO_DE_ENCRIPTACAO);

        for (int i= 0; i < 20; i++) {
            System.out.println("*********GERAANDO UM NOVO PAR DE CHAVES*********");
            KeyPair chaves = geradorDeChaves.gerarChaves();

            Encriptador encriptador  = new EncriptadorImpl(chaves, ALGORITMO_DE_ENCRIPTACAO);
            String encriptado = encriptador.encriptar("Teste de encriptação");


            System.out.println("dado encriptado" + encriptado);

            // String dado = encriptador.decriptar(encriptado);
            // System.out.println("dado original" + dado);
        }
    }
}