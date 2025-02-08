package br.edu.ifba.cassino.cliente.impl;

import br.edu.ifba.cassino.cliente.comunicacao.Cliente;
import br.edu.ifba.cassino.cliente.modelo.Jogador;
import br.edu.ifba.cassino.cliente.modelo.MesaResultadoDTO;
import br.edu.ifba.cassino.cliente.sensoriamento.SensorDeApostas;
import br.edu.ifba.cassino.cliente.util.Encriptador;
import com.google.gson.Gson;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
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
    private PublicKey chavePublica;

    public ClienteImpl(String mesaId, int totalJogadores, int jogadoresPorLeva) {
        this.mesaId = mesaId;
        this.totalJogadores = totalJogadores;
        this.jogadoresPorLeva = jogadoresPorLeva;
        this.jogadores = new ArrayList<>();
        this.melhoresJogadores = new LinkedList<>();
        try {
            this.chavePublica = carregarChavePublica();
        } catch (Exception e) {
            System.err.println("[ERRO] Falha ao carregar chave pública: " + e.getMessage());
        }
    }

    /**
     * Carrega a chave pública do servidor.
     * Complexidade: O(1), pois lê o arquivo da chave e processa um número fixo de
     * operações.
     */
    private PublicKey carregarChavePublica() throws Exception {
        File arquivo = new File("chave/chave_publica.key");
        byte[] bytes = Files.readAllBytes(arquivo.toPath());

        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    @Override
    public void configurar(int totalJogadores, int jogadoresPorLeva) {
        // Geração de jogadores
        // Complexidade: O(n), onde n é o número total de jogadores
        this.jogadores = Jogador.gerarJogadores(totalJogadores);
    }

    @Override
    public void iniciar() {
        new Thread(this).start(); // O(1) - Inicia uma nova thread
    }

    @Override
    public void run() {
        System.out.println("[" + mesaId + "] Iniciando apostas...");

        if (jogadores.size() != totalJogadores) {
            System.err.println("[ERRO] Quantidade de jogadores incorreta!");
            return;
        }

        /**
         * Processa apostas em levas.
         * Complexidade: O(n * m), onde n é o total de jogadores e m é o número de
         * apostas por jogador.
         */
        for (int i = 0; i < totalJogadores; i += jogadoresPorLeva) {
            if (i >= jogadores.size())
                break;

            int fim = i + jogadoresPorLeva;
            if (fim > jogadores.size()) {
                fim = jogadores.size(); }
            List<Jogador> levaJogadores = new ArrayList<>(jogadores.subList(i, fim));

            SensorDeApostas.gerarApostasParaJogadores(levaJogadores, 10); // O(n)

            levaJogadores.forEach(Jogador::apostar); // O(n)
            atualizarMelhoresJogadores(levaJogadores); // O(n)
            enviarDadosMesa(); // O(1)
        }
    }

    /**
     * Atualiza a lista dos melhores jogadores da mesa.
     * Complexidade: O(n), onde n é o número de jogadores na leva.
     */
    private void atualizarMelhoresJogadores(List<Jogador> levaJogadores) {
        melhoresJogadores.clear();
        Jogador primeiro = null, segundo = null, terceiro = null;

        for (Jogador jogador : levaJogadores) {
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

        if (primeiro != null)
            melhoresJogadores.add(primeiro);
        if (segundo != null)
            melhoresJogadores.add(segundo);
        if (terceiro != null)
            melhoresJogadores.add(terceiro);
    }

    /**
     * Envia os dados da mesa ao servidor.
     * Complexidade: O(n), onde n é o número de jogadores na mesa.
     */
    @Override
    public void enviarDadosMesa() {
        try {
            if (melhoresJogadores.isEmpty()) {
                System.err.println("[" + mesaId + "] Nenhum jogador qualificado para envio.");
                return;
            }

            // Calcula o lucro total da mesa
            double totalApostado = 0, totalPago = 0;
            for (Jogador jogador : jogadores) {
                totalApostado += jogador.getTotalApostado();
                totalPago += jogador.getTotalGanho();
            }
            double lucroTotalMesa = totalApostado - totalPago;

            // Criar DTO contendo apenas o lucro e os 3 melhores jogadores
            MesaResultadoDTO resultado = new MesaResultadoDTO(mesaId, lucroTotalMesa,
                    new ArrayList<>(melhoresJogadores));
            String json = new Gson().toJson(resultado);

            // Exibir JSON antes da criptografia
            System.out.println("===============================================");
            System.out.println(" Dados enviados ao SERVIDOR pela MESA " + mesaId);
            System.out.println("===============================================");
            System.out.printf(" Lucro total da mesa: %.2f\n", lucroTotalMesa);
            System.out.println(" Melhores jogadores:");
            for (Jogador jogador : melhoresJogadores) {
                double lucroJogador = jogador.getSaldo() - jogador.getSaldoInicial();
                System.out.printf(" %2d | %-20s | %13.2f | %13.2f | %8.2f%n",
                        jogador.getId(), jogador.getNomeCompleto(),
                        jogador.getSaldoInicial(), jogador.getSaldo(), lucroJogador);
            }
            System.out.println("===============================================");

            //--------------------------------------------------------------------------------------------------------------------------------------------------
            // Encriptar os dados
            String dadosCriptografados = Encriptador.encriptar(chavePublica, json.getBytes(StandardCharsets.UTF_8));

            //--------------------------------------------------------------------------------------------------------------------------------------------------
            // Enviar os dados criptografados ao servidor
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
            System.out.println("[" + mesaId + "] Resposta do servidor: " + responseCode);
            conexao.disconnect();
        } catch (Exception e) {
            System.err.println("[" + mesaId + "] Erro ao enviar dados.");
            e.printStackTrace();
        }
    }
}