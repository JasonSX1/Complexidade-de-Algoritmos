// Modificação no ClienteImpl.java para garantir envios contínuos e formatados
package br.edu.ifba.cassino.cliente.impl;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import br.edu.ifba.cassino.cliente.modelo.Jogador;

public class ClienteImpl implements Runnable {

    private static final String URL_SERVIDOR = "http://localhost:8080/cassino/jogador/";
    private static final int I, primeiroNTERVALO_ENVIO = 3000; // Enviar jogadores a cada 3 segundos
    private final Faker faker = new Faker();
    private final Gson gson = new Gson();
    private final Random random = new Random();

    public void run() {
        while (true) {
            try {
                Jogador jogador = gerarJogador();
                System.out.println("Enviando jogador: " + jogador);
                enviarDadosJogador(jogador);
                Thread.sleep(INTERVALO_ENVIO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Jogador gerarJogador() {
        return new Jogador(faker.name().firstName(), random.nextInt(100) + 1); // Garante ID entre 1 e 100
    }

    private void enviarDadosJogador(Jogador jogador) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(URL_SERVIDOR).openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        String jsonInput = gson.toJson(jogador);
        try (OutputStream os = con.getOutputStream()) {
            os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
        }
        int responseCode = con.getResponseCode();
        System.out.println("Resposta do servidor: " + responseCode);
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new ClienteImpl());
    }
}


// Modificação no OperacoesImpl.java para manter mesas corretamente
package br.edu.ifba.cassino.servidor.impl;

import java.util.*;
import br.edu.ifba.cassino.servidor.modelo.Jogador;
import br.edu.ifba.cassino.servidor.operacoes.Operacoes;

public class OperacoesImpl implements Operacoes {
    private Map<String, List<Jogador>> mesas = new HashMap<>();

    @Override
    public void gravarDadosJogador(Jogador jogador) {
        String mesaId = "mesa_" + (jogador.getId() % 5); // Distribui jogadores em até 5 mesas
        mesas.putIfAbsent(mesaId, new ArrayList<>());
        mesas.get(mesaId).add(jogador);
        System.out.println("Jogador " + jogador.getNome() + " adicionado à " + mesaId);
    }

    @Override
    public void registrarMelhorGrupo(List<Jogador> grupo) {
        System.out.println("Melhor grupo recebido: " + grupo);
    }

    @Override
    public List<Jogador> listarJogadores() {
        List<Jogador> todosJogadores = new ArrayList<>();
        for (List<Jogador> lista : mesas.values()) {
            todosJogadores.addAll(lista);
        }
        return todosJogadores;
    }
}
