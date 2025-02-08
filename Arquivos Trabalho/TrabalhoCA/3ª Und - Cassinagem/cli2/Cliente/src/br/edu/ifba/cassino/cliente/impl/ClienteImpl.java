package br.edu.ifba.cassino.cliente.impl;

import br.edu.ifba.cassino.cliente.comunicacao.Cliente;
import br.edu.ifba.cassino.cliente.modelo.Jogador;
import br.edu.ifba.cassino.cliente.modelo.MesaResultadoDTO;
import br.edu.ifba.cassino.cliente.sensoriamento.SensorDeApostas;
import br.edu.ifba.cassino.cliente.util.Encriptador;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class ClienteImpl implements Cliente, Runnable {
    private static final String URL_SERVIDOR = "http://localhost:8080/";
    private static final String URL_MESA = URL_SERVIDOR + "cassino/melhorGrupo";

    private final int totalJogadores;
    private final int jogadoresPorLeva;
    private final String mesaId;
    private List<Jogador> jogadores;
    private Queue<Jogador> melhoresJogadores;
    private double saldoFinalMesa;
    private PublicKey chavePublica; // 🔹 Agora está corretamente definido

    public ClienteImpl(String mesaId, int totalJogadores, int jogadoresPorLeva) {
        this.mesaId = mesaId;
        this.totalJogadores = totalJogadores;
        this.jogadoresPorLeva = jogadoresPorLeva;
        this.jogadores = new ArrayList<>();
        this.melhoresJogadores = new LinkedList<>();
        this.saldoFinalMesa = 0.0;
        try {
            this.chavePublica = carregarChavePublica();
        } catch (Exception e) {
            System.err.println("[ERRO] Falha ao carregar chave pública: " + e.getMessage());
        }
    }

    private PublicKey carregarChavePublica() throws Exception {
        File arquivo = new File("chave/chave_publica.key");
        byte[] bytes = Files.readAllBytes(arquivo.toPath());

        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    @Override
    public void configurar(int totalJogadores, int jogadoresPorLeva) {
        this.jogadores = Jogador.gerarJogadores(totalJogadores);
    }

    @Override
    public void iniciar() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("[" + mesaId + "] Iniciando apostas...");

        if (jogadores.size() != totalJogadores) {
            System.err.println("[ERRO] Quantidade de jogadores incorreta! Esperado: " + totalJogadores + ", Obtido: "
                    + jogadores.size());
            return;
        }

        for (int i = 0; i < totalJogadores; i += jogadoresPorLeva) {
            if (i >= jogadores.size()) {
                System.err.println("[ERRO] Tentativa de acessar subList além do tamanho da lista. Encerrando loop.");
                break;
            }

            int fim = Math.min(i + jogadoresPorLeva, jogadores.size());
            List<Jogador> levaJogadores = new ArrayList<>(jogadores.subList(i, fim));

            SensorDeApostas.gerarApostasParaJogadores(levaJogadores, 10);

            levaJogadores.forEach(Jogador::apostar);
            atualizarMelhoresJogadores(levaJogadores);
            enviarDadosMesa();
        }
    }

    private void atualizarMelhoresJogadores(List<Jogador> levaJogadores) {
        for (Jogador jogador : levaJogadores) {
            if (melhoresJogadores.size() < jogadoresPorLeva) {
                melhoresJogadores.add(jogador);
            } else {
                Jogador piorJogador = melhoresJogadores.peek();
                if (jogador.getSaldo() > piorJogador.getSaldo()) {
                    melhoresJogadores.poll();
                    melhoresJogadores.add(jogador);
                }
            }
        }
    }

    @Override
    public void enviarDadosMesa() {
        try {
            if (melhoresJogadores.isEmpty()) {
                System.err.println("[MESA " + mesaId + "] Nenhum jogador qualificado para envio.");
                return;
            }
    
            // 🔹 Seleciona os 3 melhores jogadores manualmente
            List<Jogador> top3Jogadores = new ArrayList<>();
            Jogador primeiro = null, segundo = null, terceiro = null;
    
            for (Jogador jogador : melhoresJogadores) {
                double lucro = jogador.getSaldo() - jogador.getSaldoInicial();
    
                if (primeiro == null || lucro > (primeiro.getSaldo() - primeiro.getSaldoInicial())) {
                    terceiro = segundo;
                    segundo = primeiro;
                    primeiro = jogador;
                } else if (segundo == null || lucro > (segundo.getSaldo() - segundo.getSaldoInicial())) {
                    terceiro = segundo;
                    segundo = jogador;
                } else if (terceiro == null || lucro > (terceiro.getSaldo() - terceiro.getSaldoInicial())) {
                    terceiro = jogador;
                }
            }
    
            if (primeiro != null) top3Jogadores.add(primeiro);
            if (segundo != null) top3Jogadores.add(segundo);
            if (terceiro != null) top3Jogadores.add(terceiro);
    
            // 🔹 Calcular o lucro total da mesa corretamente
            double totalApostado = 0;
            double totalPago = 0;
            for (Jogador jogador : jogadores) {
                totalApostado += jogador.getTotalApostado(); // Soma todas as apostas feitas
                totalPago += jogador.getTotalGanho(); // Soma todos os pagamentos aos jogadores
            }
            double lucroTotalMesa = totalApostado - totalPago;
    
            // 🔹 Criar DTO contendo apenas o lucro e os 3 melhores jogadores
            MesaResultadoDTO resultado = new MesaResultadoDTO(mesaId, lucroTotalMesa, top3Jogadores);
            String json = new Gson().toJson(resultado);
    
            // 🔑 Exibir JSON antes da criptografia
            System.out.println("===============================================");
            System.out.println(" Dados enviados ao SERVIDOR pela MESA " + mesaId);
            System.out.println("===============================================");
            System.out.printf(" Lucro total da mesa: %.2f\n", lucroTotalMesa);
            System.out.println(" Melhores jogadores:");
            for (Jogador jogador : top3Jogadores) {
                double lucroJogador = jogador.getSaldo() - jogador.getSaldoInicial();
                System.out.printf(" %2d | %-20s | %13.2f | %13.2f | %8.2f%n",
                        jogador.getId(), jogador.getNomeCompleto(),
                        jogador.getSaldoInicial(), jogador.getSaldo(), lucroJogador);
            }
            System.out.println("===============================================");
    
            // 🔑 Encriptar os dados e enviar ao servidor
            String dadosCriptografados = Encriptador.encriptar(chavePublica, json.getBytes(StandardCharsets.UTF_8));
            System.out.println("[DEBUG] Dados criptografados enviados ao servidor: " + dadosCriptografados);
    
            // 🔹 Enviar os dados criptografados ao servidor
            URL url = new URL(URL_MESA);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
    
            conexao.setDoOutput(true);
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json");
    
            try (OutputStream os = conexao.getOutputStream()) {
                byte[] input = dadosCriptografados.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
    
            int responseCode = conexao.getResponseCode();
            System.out.println("[MESA " + mesaId + "] Resposta do servidor: " + responseCode);
            conexao.disconnect();
        } catch (Exception e) {
            System.err.println("[MESA " + mesaId + "] Erro ao enviar dados.");
            e.printStackTrace();
        }
    }
    

}
