package br.edu.ifba.cassino.cliente;

import br.edu.ifba.cassino.cliente.impl.ClienteImpl;

import java.util.Random;

public class App {
    public static void main(String[] args) {
        final int jogadoresPorMesa = 5;
        final int totalMesas = 10;

        // Criação das mesas iniciais
        for (int i = 0; i < totalMesas; i++) {
            String mesaId = "Mesa-" + (i + 1);
            ClienteImpl clienteMesa = new ClienteImpl(mesaId);
            clienteMesa.configurar(jogadoresPorMesa);

            Thread threadMesa = new Thread(clienteMesa);
            threadMesa.setName(mesaId);
            threadMesa.start();

            System.out.println("Iniciando thread para " + threadMesa.getName());
        }

        // Adicionar jogadores continuamente
        new Thread(() -> {
            Random random = new Random();
            while (true) {
                try {
                    Thread.sleep(5000); // Intervalo entre novos jogadores
                    String mesaId = "Mesa-Adicional-" + random.nextInt(100);
                    ClienteImpl clienteMesa = new ClienteImpl(mesaId);
                    clienteMesa.configurar(random.nextInt(jogadoresPorMesa) + 1);

                    Thread threadMesa = new Thread(clienteMesa);
                    threadMesa.setName(mesaId);
                    threadMesa.start();

                    System.out.println("Adicionando novos jogadores na " + threadMesa.getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
