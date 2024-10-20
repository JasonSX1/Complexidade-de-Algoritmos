package edu.ifba.avaliacao.cassino.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.ifba.avaliacao.cassino.sensoriamento.Monitoramento;

public class SensoriamentoImpl implements Monitoramento<Aposta> {

    private static final String[] TIPOS_DE_APOSTA = {"par", "ímpar", "preto", "vermelho"};
    private static final int NUMERO_MAXIMO = 36;

    /**
     * Este método gera aleatoriamente uma lista de apostas na roleta.
     * A complexidade é O(N) porque há um único loop que cresce proporcionalmente ao número de apostas.
     */
    @Override
    public List<Aposta> gerar(int totalApostas) {
        List<Aposta> apostas = new ArrayList<>();

        Random randomizador = new Random();
        for (int i = 0; i < totalApostas; i++) {
            // Gerar o tipo de aposta
            String tipoDeAposta;
            if (randomizador.nextBoolean()) {
                // Escolher aleatoriamente entre par, ímpar, preto ou vermelho
                tipoDeAposta = TIPOS_DE_APOSTA[randomizador.nextInt(TIPOS_DE_APOSTA.length)];
            } else {
                // Escolher um número aleatório entre 0 e 36
                tipoDeAposta = String.valueOf(randomizador.nextInt(NUMERO_MAXIMO + 1));
            }

            // Criar a aposta
            Aposta aposta = new Aposta(tipoDeAposta);
            apostas.add(aposta);
        }

        return apostas;
    }
}