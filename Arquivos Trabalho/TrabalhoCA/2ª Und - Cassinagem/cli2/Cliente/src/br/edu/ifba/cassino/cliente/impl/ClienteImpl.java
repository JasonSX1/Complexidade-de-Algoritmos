package br.edu.ifba.cassino.cliente.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.Random;

import com.github.javafaker.Faker;
import com.google.gson.Gson;

import br.edu.ifba.cassino.cliente.modelo.Aposta;
import br.edu.ifba.cassino.cliente.modelo.Jogador;
import br.edu.ifba.cassino.cliente.modelo.Roleta;

public class ClienteImpl implements Runnable {

    private static final String URL_SERVIDOR = "http://localhost:8080/";
    private static final String URL_JOGADOR = URL_SERVIDOR + "cassino/jogador/";

    private static final int TAMANHO_MAXIMO_HISTORICO = 3;

    private List<Jogador> jogadores;
    private Queue<Jogador> melhorGrupo;
    private double lucroMelhorGrupo;

    public void configurar(int quantidadeJogadores) {
        this.jogadores = gerarJogadores(quantidadeJogadores);
        this.melhorGrupo = new LinkedList<>();
        this.lucroMelhorGrupo = Double.NEGATIVE_INFINITY;
    }

    @Override
    public void run() {
        for (Jogador jogador : jogadores) {
            gerarApostasParaJogador(jogador);
            atualizarMelhorGrupo(jogador);

            // Enviar dados ao servidor quando o jogador terminar as apostas
            enviarDadosJogadorAoServidor(jogador);
        }
    }

    private List<Jogador> gerarJogadores(int quantidade) {
        Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));
        Random random = new Random();
        List<Jogador> jogadores = new ArrayList<>();

        for (int i = 0; i < quantidade; i++) {
            String nome = faker.name().fullName();
            double saldoInicial = 500 + random.nextDouble() * 1500; // Saldo entre 500 e 2000
            int totalApostas = random.nextInt(16) + 10; // Entre 10 e 25 apostas
    
            jogadores.add(new Jogador(i + 1, nome, saldoInicial, totalApostas));
        }

        return jogadores;
    }

    private void gerarApostasParaJogador(Jogador jogador) {
        Random random = new Random();
        Roleta roleta = new Roleta();

        for (int i = 0; i < jogador.getTotalApostas(); i++) {
            roleta.girar();

            double entrada = 10 + random.nextDouble() * 40;
            String tipoAposta = SensorDeApostas.escolherTipoAposta(random);
            double resultado = -entrada;

            Aposta aposta = switch (tipoAposta) {
                case "NUMERO" -> SensorDeApostas.gerarApostaNumero(roleta, entrada, random);
                case "COR" -> SensorDeApostas.gerarApostaCor(roleta, entrada, random);
                case "PARIDADE" -> SensorDeApostas.gerarApostaParidade(roleta, entrada, random);
                default -> null;
            };

            if (aposta != null) {
                jogador.adicionarAposta(aposta);
            }
        }
    }

    private void atualizarMelhorGrupo(Jogador jogador) {
        melhorGrupo.add(jogador);
        if (melhorGrupo.size() > TAMANHO_MAXIMO_HISTORICO) {
            melhorGrupo.poll();
        }

        double lucroAtual = melhorGrupo.stream().mapToDouble(Jogador::getSaldoAtual).sum();
        if (lucroAtual > lucroMelhorGrupo) {
            lucroMelhorGrupo = lucroAtual;
            enviarMelhorGrupoAoServidor();
        }
    }

    private void enviarMelhorGrupoAoServidor() {
        try {
            StringBuilder dadosGrupo = new StringBuilder();
            for (Jogador jogador : melhorGrupo) {
                dadosGrupo.append(jogador.toString()).append("\n");
            }

            // Simulação de envio (substituir por código real de envio HTTP)
            System.out.println("Enviando melhor grupo ao servidor:\n" + dadosGrupo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void enviarDadosJogadorAoServidor(Jogador jogador) {
        try {
            // Configuração da URL do endpoint
            URL url = new URL(URL_JOGADOR);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
    
            // Configurar a conexão como POST
            conexao.setDoOutput(true);
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json");
    
            // Serializar o objeto Jogador em JSON
            String jsonInputString = new Gson().toJson(jogador);
    
            // Enviar os dados JSON no corpo da requisição
            try (OutputStream os = conexao.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
    
            // Verificar a resposta do servidor
            int responseCode = conexao.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Dados enviados com sucesso para o jogador: " + jogador.getNome());
            } else {
                throw new Exception("Falha ao enviar dados do jogador. Código de resposta: " + responseCode);
            }
    
            conexao.disconnect();
        } catch (Exception e) {
            System.err.println("Erro ao enviar dados do jogador: " + jogador.getNome());
            e.printStackTrace();
        }
    }    
}
