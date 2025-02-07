package src.br.edu.ifba.encriptacao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

import src.br.edu.ifba.encriptacao.aleatoriedade.GeradorDeAleatoriedadeReal;
import src.br.edu.ifba.encriptacao.chaves.GeradorDeChaves;
import src.br.edu.ifba.encriptacao.excecoes.FalhaGeracaoDeChaves;

public class GeradorDeChavesImpl implements GeradorDeChaves<GeradorDeAleatoriedadeReal> {
    private KeyPairGenerator keyGen;
    private KeyPair chavePar;
    private GeradorDeAleatoriedadeReal geradorAleatoriedade;

    @Override
    public void inicializar(GeradorDeAleatoriedadeReal geradorDeAleatoriedade, String algoritmoDeEncriptacao) {
        try {
            this.geradorAleatoriedade = geradorDeAleatoriedade;
            keyGen = KeyPairGenerator.getInstance(algoritmoDeEncriptacao);
            keyGen.initialize(2048, geradorAleatoriedade); // Usa a aleatoriedade do áudio!
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao inicializar gerador de chaves: " + e.getMessage());
        }
    }

    @Override
    public KeyPair gerarChaves() throws FalhaGeracaoDeChaves {
        try {
            chavePar = keyGen.generateKeyPair();
            return chavePar;
        } catch (Exception e) {
            throw new FalhaGeracaoDeChaves("Erro ao gerar chaves: " + e.getMessage());
        }
    }

    @Override
    public void gerarChaves(String arquivoChavePrivada, String arquivoChavePublica) throws FalhaGeracaoDeChaves {
        try {
            gerarChaves(); // Gera o par de chaves antes de salvar

            // Salvar chave privada
            FileOutputStream fosPriv = new FileOutputStream(new File(arquivoChavePrivada));
            fosPriv.write(chavePar.getPrivate().getEncoded());
            fosPriv.close();

            // Salvar chave pública
            FileOutputStream fosPub = new FileOutputStream(new File(arquivoChavePublica));
            fosPub.write(chavePar.getPublic().getEncoded());
            fosPub.close();
        } catch (IOException e) {
            throw new FalhaGeracaoDeChaves("Erro ao salvar chaves: " + e.getMessage());
        }
    }

    @Override
    public void finalizar() throws FalhaGeracaoDeChaves {
        try {
            if (geradorAleatoriedade != null) {
                geradorAleatoriedade.finalizar();
            }
        } catch (Exception e) {
            throw new FalhaGeracaoDeChaves("Erro ao finalizar gerador de chaves: " + e.getMessage());
        }
    }
}
