package br.edu.ifba.cassino.cliente;

import br.edu.ifba.cassino.cliente.impl.ClienteImpl;

public class App {
    public static void main(String[] args) {
        final int jogadoresPorMesa = 5; // Número de jogadores por mesa
        final int totalMesas = 10; // Total de mesas no cassino

        // Criação e inicialização das mesas
        for (int i = 0; i < totalMesas; i++) {
            String mesaId = "Mesa-" + (i + 1); // Identificação única para cada mesa
            ClienteImpl clienteMesa = new ClienteImpl(mesaId);
            clienteMesa.configurar(jogadoresPorMesa);

            // Criação de uma thread para cada mesa
            Thread threadMesa = new Thread(clienteMesa);
            threadMesa.setName(mesaId);
            threadMesa.start();

            System.out.println("Iniciando thread para " + threadMesa.getName());
        }
    }
}
