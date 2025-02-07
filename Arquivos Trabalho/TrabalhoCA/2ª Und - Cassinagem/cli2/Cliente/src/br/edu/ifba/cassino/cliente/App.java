package br.edu.ifba.cassino.cliente;

import br.edu.ifba.cassino.cliente.impl.ClienteImpl;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static final int QUANTIDADE_MESAS = 10; // Número de mesas no cassino
    private static final int TOTAL_JOGADORES_POR_MESA = 1000; // Total de jogadores por mesa
    private static final int JOGADORES_POR_LEVA = 50; // Jogadores processados por rodada na mesa

    public static void main(String[] args) {
        
        System.out.println("[CLIENTE] Iniciando Cassino...");

        List<Thread> threadsMesas = new ArrayList<>();

        for (int i = 1; i <= QUANTIDADE_MESAS; i++) {
            String mesaId = "MESA " + i;
            System.out.println("[APP] Criando " + mesaId + " com " + TOTAL_JOGADORES_POR_MESA + " jogadores...");
            
            // Criando uma instância do Cliente para cada mesa
            ClienteImpl cliente = new ClienteImpl(mesaId, TOTAL_JOGADORES_POR_MESA, JOGADORES_POR_LEVA);
            cliente.configurar(TOTAL_JOGADORES_POR_MESA, JOGADORES_POR_LEVA);
            Thread threadMesa = new Thread(cliente);
            threadsMesas.add(threadMesa);
            threadMesa.start();
        }

        // Aguarda todas as mesas terminarem
        for (Thread thread : threadsMesas) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("[APP] Erro ao aguardar finalização da mesa: " + e.getMessage());
            }
        }

        System.out.println("[CLIENTE] Todas as mesas finalizaram suas operações.");
    }
}
