package br.edu.ifba.cassino.cliente;

import br.edu.ifba.cassino.cliente.impl.ClienteImpl;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static final int QUANTIDADE_MESAS = 10; // Número de mesas no cassino
    private static final int TOTAL_JOGADORES_POR_MESA = 100; // Total de jogadores por mesa
    private static final int JOGADORES_POR_LEVA = 20; // Jogadores processados por rodada na mesa

    public static void main(String[] args) {

        System.out.println("[CLIENTE] Iniciando Cassino...");

        List<Thread> threadsMesas = new ArrayList<>();

        // Criação das mesas e inicialização das threads.
        // Complexidade: O(n), onde n é QUANTIDADE_MESAS.
        for (int i = 1; i <= QUANTIDADE_MESAS; i++) {
            String mesaId = "MESA " + i;
            System.out.println("[APP] Criando " + mesaId + " com " + TOTAL_JOGADORES_POR_MESA + " jogadores...");

            ClienteImpl cliente = new ClienteImpl(mesaId, TOTAL_JOGADORES_POR_MESA, JOGADORES_POR_LEVA);
            cliente.configurar(TOTAL_JOGADORES_POR_MESA, JOGADORES_POR_LEVA);
            Thread threadMesa = new Thread(cliente);
            threadsMesas.add(threadMesa);
            threadMesa.start(); // O(1) - Inicia uma nova thread
        }

        
        // Aguarda todas as mesas finalizarem suas operações.
        // Complexidade: O(n), onde n é o número de threads (mesas).
        for (Thread thread : threadsMesas) {
            try {
                thread.join(); // O(1) - Aguarda a finalização de cada thread
            } catch (InterruptedException e) {
                System.err.println("[APP] Erro ao aguardar finalização da mesa: " + e.getMessage());
            }
        }

        System.out.println("[CLIENTE] Todas as mesas finalizaram suas operações.");
    }
}