package br.edu.ifba.encriptacao.impl;

import br.edu.ifba.encriptacao.aleatoriedade.GeradorDeAleatoriedadeReal;
import br.edu.ifba.encriptacao.chaves.GeradorDeChaves;
import br.edu.ifba.encriptacao.excecoes.FalhaGeracaoDeChaves;
import br.edu.ifba.*;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class GeradorDeChavesImpl implements GeradorDeChaves<GeradorDeAleatoriedadeReal> {

    private static final int TAMANHO_DAS_CHAVES = 2048;

    private GeradorDeAleatoriedadeReal geradorDeAleatoriedadeReal = null;
    private String algoritmoDeEncriptacao = null;

    @Override
    public void inicializar(GeradorDeAleatoriedadeReal geradorDeAleatoriedadeReal, String algoritmoDeEncriptacao) {
        this.geradorDeAleatoriedadeReal = geradorDeAleatoriedadeReal;
        this.algoritmoDeEncriptacao = algoritmoDeEncriptacao;
    }

    @Override
    public KeyPair gerarChaves() throws FalhaGeracaoDeChaves {
        KeyPair chaves;

        try {
            KeyPairGenerator geradorDeChaves = KeyPairGenerator.getInstance(algoritmoDeEncriptacao);
            geradorDeChaves.initialize(TAMANHO_DAS_CHAVES, geradorDeAleatoriedadeReal);

            chaves = geradorDeChaves.generateKeyPair();
        } catch (Exception e) {
            throw new FalhaGeracaoDeChaves("erro gerando fallha na geração de chaves");
        }

        return chaves;
    }

    @Override
    public void finalizar() throws FalhaGeracaoDeChaves {
        geradorDeAleatoriedadeReal.finalizar();
    } // Add this closing brace
}