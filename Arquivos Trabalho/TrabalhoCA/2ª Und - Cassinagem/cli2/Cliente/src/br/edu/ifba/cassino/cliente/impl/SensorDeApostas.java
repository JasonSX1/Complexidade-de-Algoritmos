package br.edu.ifba.cassino.cliente.impl;


import java.util.Random;

import br.edu.ifba.cassino.cliente.modelo.Aposta;
import br.edu.ifba.cassino.cliente.modelo.Roleta;

public class SensorDeApostas {

    // Escolhe o tipo de aposta aleatoriamente
    public static String escolherTipoAposta(Random random) {
        int tipo = random.nextInt(3);
        switch (tipo) {
            case 0:
                return "NUMERO";
            case 1:
                return "COR";
            case 2:
                return "PARIDADE";
            default:
                return "NUMERO";
        }
    }

    // Gera uma aposta do tipo NUMERO
    public static Aposta gerarApostaNumero(Roleta roleta, double entrada, Random random) {
        int numeroApostado = random.nextInt(37); // Números entre 0 e 36
        double resultado = (numeroApostado == roleta.getNumeroSorteado()) ? entrada * 3 : -entrada;
        return new Aposta(entrada, "NUMERO", numeroApostado, null, null,
                          roleta.getNumeroSorteado(), roleta.getCorSorteada(), roleta.getTipoSorteado(), resultado);
    }

    // Gera uma aposta do tipo COR
    public static Aposta gerarApostaCor(Roleta roleta, double entrada, Random random) {
        String corApostada = random.nextBoolean() ? "VERMELHO" : "PRETO";
        double resultado = (corApostada.equals(roleta.getCorSorteada())) ? entrada * 1.5 : -entrada;
        return new Aposta(entrada, "COR", -1, corApostada, null,
                          roleta.getNumeroSorteado(), roleta.getCorSorteada(), roleta.getTipoSorteado(), resultado);
    }

    // Gera uma aposta do tipo PARIDADE
    public static Aposta gerarApostaParidade(Roleta roleta, double entrada, Random random) {
        String paridadeApostada = random.nextBoolean() ? "PAR" : "ÍMPAR";
        double resultado = (paridadeApostada.equals(roleta.getTipoSorteado())) ? entrada * 1.5 : -entrada;
        return new Aposta(entrada, "PARIDADE", -1, null, paridadeApostada,
                          roleta.getNumeroSorteado(), roleta.getCorSorteada(), roleta.getTipoSorteado(), resultado);
    }
}
