package br.edu.ifba.pacientes.cliente.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.edu.ifba.pacientes.cliente.modelo.Biometria;
import br.edu.ifba.pacientes.cliente.sensoriamento.Sensoriamento;

public class SensoriamentoImpl implements Sensoriamento<Biometria> {

    private static final int TEMPERATURA_NORMAL = 36;
    private static final int BATIMENTOS_NORMAIS = 80;

    private static final int OSCILACAO_MAXIMA = 10;

    /**
     * este metodo gera randomicamente as leituras de temperatura
     * 
     * a sua complexidade eh linear, O(N), porque tem um unico loop no seu codigo e a complexidade cresce na mesma proporcao quanto cresce a entrada de dados
     */
    @Override
    public List<Biometria> gerar(int totalLeituras) {
        List<Biometria> leituras = new ArrayList<>();

        Random randomizador = new Random();
        for (int i = 0; i < totalLeituras; i++) {
            int oscilacao = TEMPERATURA_NORMAL * randomizador.nextInt(OSCILACAO_MAXIMA)/100;

            int temperatura = (randomizador.nextBoolean()? TEMPERATURA_NORMAL + oscilacao: TEMPERATURA_NORMAL - oscilacao);
            int batimentos = (randomizador.nextBoolean()? BATIMENTOS_NORMAIS + oscilacao: BATIMENTOS_NORMAIS - oscilacao);

            Biometria leitura = new Biometria(batimentos, temperatura);
            leituras.add(leitura);
        }

        return leituras;
    }
    
}
