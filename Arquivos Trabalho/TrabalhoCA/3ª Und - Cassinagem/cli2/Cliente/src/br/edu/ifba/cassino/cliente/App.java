package br.edu.ifba.cassino.cliente;

import br.edu.ifba.cassino.cliente.impl.ClienteImpl;
import br.edu.ifba.encriptacao.aleatoriedade.GeradorDeAleatoriedadeReal;
import br.edu.ifba.encriptacao.chaves.GeradorDeChaves;
import br.edu.ifba.encriptacao.impl.GeradorDeChavesImpl;
import br.edu.ifba.encriptacao.excecoes.FalhaGeracaoDeChaves;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static final int QUANTIDADE_MESAS = 1; // Número de mesas no cassino
    private static final int TOTAL_JOGADORES_POR_MESA = 10; // Total de jogadores por mesa
    private static final int JOGADORES_POR_LEVA = 100; // Jogadores processados por rodada na mesa
    private static final String CAMINHO_AUDIO = "resources/audio/random_audio.mp3"; // 🔹 Áudio usado para encriptação

    public static void main(String[] args) {
        System.out.println("[CLIENTE] Iniciando Cassino...");

        List<Thread> threadsMesas = new ArrayList<>();

        try {
            // 🔹 Inicializa o gerador de aleatoriedade com áudio
            GeradorDeAleatoriedadeReal geradorDeAleatoriedade = new GeradorDeAleatoriedadeReal(CAMINHO_AUDIO);

            // 🔹 Inicializa o gerador de chaves
            GeradorDeChaves<GeradorDeAleatoriedadeReal> geradorDeChaves = new GeradorDeChavesImpl();
            geradorDeChaves.inicializar(geradorDeAleatoriedade, "RSA");

            for (int i = 1; i <= QUANTIDADE_MESAS; i++) {
                String mesaId = "MESA " + i;
                System.out.println("[APP] Criando " + mesaId + " com " + TOTAL_JOGADORES_POR_MESA + " jogadores...");

                // 🔹 Criando uma instância do ClienteImpl com criptografia
                ClienteImpl cliente = new ClienteImpl(mesaId, TOTAL_JOGADORES_POR_MESA, JOGADORES_POR_LEVA, geradorDeChaves);
                cliente.configurar(TOTAL_JOGADORES_POR_MESA, JOGADORES_POR_LEVA);
                
                Thread threadMesa = new Thread(cliente);
                threadsMesas.add(threadMesa);
                threadMesa.start();
            }

            // 🔹 Aguarda todas as mesas terminarem
            for (Thread thread : threadsMesas) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    System.err.println("[APP] Erro ao aguardar finalização da mesa: " + e.getMessage());
                }
            }

            System.out.println("[CLIENTE] Todas as mesas finalizaram suas operações.");

            // 🔹 Finaliza os geradores
            geradorDeChaves.finalizar();
            geradorDeAleatoriedade.finalizar();

        } catch (FalhaGeracaoDeChaves | NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            System.err.println("[APP] Erro crítico durante a inicialização: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
