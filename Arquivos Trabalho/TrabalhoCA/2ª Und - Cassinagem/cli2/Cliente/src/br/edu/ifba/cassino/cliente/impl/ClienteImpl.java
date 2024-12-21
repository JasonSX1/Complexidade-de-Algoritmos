// Classe ClienteImpl
package br.edu.ifba.cassino.cliente.impl;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
import br.edu.ifba.cassino.cliente.modelo.JogadorDTO;
import br.edu.ifba.cassino.cliente.modelo.Roleta;

public class ClienteImpl implements Runnable {

    private static final String URL_SERVIDOR = "http://localhost:8080/";
    private static final String URL_JOGADOR = URL_SERVIDOR + "cassino/jogador/";

    private static final int TAMANHO_MAXIMO_HISTORICO = 3;

    private List<Jogador> jogadores;
    private Queue<Jogador> melhorGrupo;
    private double lucroMelhorGrupo;
    private final String mesaId;

    public ClienteImpl(String mesaId) {
        this.mesaId = mesaId;
        this.jogadores = new ArrayList<>();
        this.melhorGrupo = new LinkedList<>();
        this.lucroMelhorGrupo = Double.NEGATIVE_INFINITY;
    }

    public void configurar(int quantidadeJogadores) {
        this.jogadores = gerarJogadores(quantidadeJogadores);
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
        Roleta roleta = new Roleta(); // Cria uma nova instância da roleta
        Random random = new Random();
    
        for (int i = 0; i < jogador.getTotalApostas(); i++) {
            roleta.girar(); // Gira a roleta
    
            int entrada = random.nextInt(100) + 1; // Gera uma entrada entre 1 e 100 (valores inteiros)
            String tipoAposta = SensorDeApostas.escolherTipoAposta(random);
    
            int resultado; // Resultado da aposta, que será inteiro
            Aposta aposta = null;
    
            // Escolhe o tipo de aposta e calcula o resultado
            switch (tipoAposta) {
                case "NUMERO" -> {
                    aposta = SensorDeApostas.gerarApostaNumero(roleta, entrada, random);
                    resultado = (int) Math.round(aposta.getResultado()); // Converte o resultado para int
                }
                case "COR" -> {
                    aposta = SensorDeApostas.gerarApostaCor(roleta, entrada, random);
                    resultado = (int) Math.round(aposta.getResultado()); // Converte o resultado para int
                }
                case "PARIDADE" -> {
                    aposta = SensorDeApostas.gerarApostaParidade(roleta, entrada, random);
                    resultado = (int) Math.round(aposta.getResultado()); // Converte o resultado para int
                }
                default -> resultado = 0; // Caso nenhum tipo de aposta seja escolhido
            }
    
            if (aposta != null) {
                jogador.adicionarAposta(aposta);
                jogador.adicionarHistoricoAposta(tipoAposta + "/" + resultado); // Adiciona ao histórico como string formatada
            }
        }
    
        // Atualiza o saldo final do jogador após todas as apostas
        jogador.setSaldoFinal(jogador.getSaldoAtual());
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
            URL url = new URL(URL_SERVIDOR + "cassino/melhorGrupo");
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            
            conexao.setDoOutput(true);
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json");

            // Serializar o grupo em JSON
            String jsonInputString = new Gson().toJson(melhorGrupo);
            System.out.println("Enviando melhor grupo: " + jsonInputString); // Log para depuração

            try (OutputStream os = conexao.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conexao.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Melhor grupo enviado com sucesso.");
            } else {
                throw new Exception("Falha ao enviar o melhor grupo. Código de resposta: " + responseCode);
            }

            conexao.disconnect();
        } catch (Exception e) {
            System.err.println("Erro ao enviar melhor grupo.");
            e.printStackTrace();
        }
    }

    private void enviarDadosJogadorAoServidor(Jogador jogador) {
        try {
            JogadorDTO jogadorDTO = converterParaDTO(jogador); // Converter para DTO
            String jsonInputString = new Gson().toJson(jogadorDTO);
    
            URL url = new URL(URL_JOGADOR);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
    
            conexao.setDoOutput(true);
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json");
    
            try (OutputStream os = conexao.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
    
            if (conexao.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Falha ao enviar dados do jogador. Código de resposta: " + conexao.getResponseCode());
            }
    
            conexao.disconnect();
        } catch (Exception e) {
            System.err.println("Erro ao enviar dados do jogador: " + jogador.getNome());
            e.printStackTrace();
        }
    }    

    private JogadorDTO converterParaDTO(Jogador jogador) {
    return new JogadorDTO(
        jogador.getId(),
        jogador.getNome(),
        jogador.getSaldoInicial(),
        jogador.getSaldoFinal(),
        jogador.getMesaId()
    );
}

}
