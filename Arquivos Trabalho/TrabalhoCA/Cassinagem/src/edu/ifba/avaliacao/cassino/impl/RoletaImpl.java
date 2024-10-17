package edu.ifba.avaliacao.cassino.impl;

import java.util.Random;

import edu.ifba.avaliacao.cassino.roleta.Roleta;

public class RoletaImpl implements Roleta {

    private Random random = new Random();

    @Override
    public ResultadoRoleta sortear() {
        int numero = random.nextInt(37); // 0-36 na roleta tradicional
        String cor = (numero == 0) ? "Verde" : (numero % 2 == 0 ? "Preto" : "Vermelho");
        String tipo = (numero == 0) ? "Nenhum" : (numero % 2 == 0 ? "Par" : "Ímpar");

        return new ResultadoRoleta(numero, cor, tipo);
    }

    @Override
    public double calcularRetorno(Aposta aposta, ResultadoRoleta resultado) {
        // Simulação de cálculo de retorno para apostas
        // Supondo que o jogador aposte em um número, cor ou tipo (par/ímpar)
        double retorno = 0;

        // Exemplo: se a aposta for no número correto, o retorno é 36 vezes o valor da aposta
        if (aposta.getNumeroApostado() == resultado.getNumero()) {
            retorno = aposta.getValor() * 36;
        }
        // Se o jogador apostou na cor correta, o retorno é 2 vezes o valor da aposta
        else if (aposta.getCorApostada().equals(resultado.getCor())) {
            retorno = aposta.getValor() * 2;
        }
        // Se o jogador apostou em ímpar/par corretamente, o retorno é 2 vezes o valor da aposta
        else if (aposta.getTipoApostado().equals(resultado.getTipo())) {
            retorno = aposta.getValor() * 2;
        }
        return retorno;
    }
}
