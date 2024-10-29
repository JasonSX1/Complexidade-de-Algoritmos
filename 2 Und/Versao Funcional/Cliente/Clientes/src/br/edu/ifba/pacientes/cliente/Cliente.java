package br.edu.ifba.pacientes.cliente;

import java.util.Random;

import br.edu.ifba.pacientes.cliente.comunicacao.Comunicacao;
import br.edu.ifba.pacientes.cliente.impl.comunicacaoimpl;

public class Cliente {
    public static void main(String[] args) throws Exception {
        Comunicacao comunicacao = new comunicacaoimpl();
        Random randomizador = new Random();
        
        for (int i = 0; i < 10000; i++) {
            comunicacao.enviar(randomizador.nextInt(i+1) + "", randomizador.nextInt(100), randomizador.nextInt(40));

            Thread.sleep(100);
        }
    }
}
