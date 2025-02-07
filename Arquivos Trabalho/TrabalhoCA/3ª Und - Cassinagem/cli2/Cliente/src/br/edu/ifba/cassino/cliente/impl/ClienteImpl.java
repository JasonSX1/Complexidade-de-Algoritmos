package br.edu.ifba.cassino.cliente.impl;

import br.edu.ifba.cassino.cliente.comunicacao.Cliente;
import br.edu.ifba.cassino.cliente.modelo.Jogador;
import br.edu.ifba.cassino.cliente.modelo.MesaResultadoDTO;
import br.edu.ifba.cassino.cliente.sensoriamento.SensorDeApostas;
import com.google.gson.Gson;

import javax.crypto.Cipher;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class ClienteImpl implements Cliente, Runnable {
    private static final String URL_SERVIDOR = "http://localhost:8080/";
    private static final String URL_MESA = URL_SERVIDOR + "cassino/melhorGrupo";

    private static final String CAMINHO_CHAVE_PUBLICA = "chaves/publica.chv"; // Caminho da chave pública
    private static final String ALGORITMO_ENCRIPTACAO = "RSA";

    private PublicKey chavePublica;
    
    private final int totalJogadores;
    private final int jogadoresPorLeva;
    private final String mesaId;
    private List<Jogador> jogadores;
    private Queue<Jogador> melhoresJogadores;
    private double saldoFinalMesa;

    public ClienteImpl(String mesaId, int totalJogadores, int jogadoresPorLeva) throws Exception {
        this.mesaId = mesaId;
        this.totalJogadores = totalJogadores;
        this.jogadoresPorLeva = jogadoresPorLeva;
        this.jogadores = new ArrayList<>();
        this.melhoresJogadores = new LinkedList<>();
        this.saldoFinalMesa = 0.0;
        this.chavePublica = carregarChavePublica();
    }

    private PublicKey carregarChavePublica() throws Exception {
        File arquivo = new File(CAMINHO_CHAVE_PUBLICA);
        FileInputStream fis = new FileInputStream(arquivo);
        byte[] bytes = fis.readAllBytes();
        fis.close();

        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITMO_ENCRIPTACAO);
        return keyFactory.generatePublic(spec);
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
        System.out.println("[MESA " + mesaId + "] Iniciando apostas...");

        for (int i = 0; i < totalJogadores; i += jogadoresPorLeva) {
            List<Jogador> levaJogadores = jogadores.subList(i, Math.min(i + jogadoresPorLeva, jogadores.size()));
            SensorDeApostas.gerarApostasParaJogadores(levaJogadores, 5);
            levaJogadores.forEach(Jogador::apostar);
            atualizarMelhoresJogadores(levaJogadores);
            enviarDadosMesa();
        }
    }

    private void atualizarMelhoresJogadores(List<Jogador> leva) {
        melhoresJogadores.clear();
        double maiorLucro = Double.NEGATIVE_INFINITY;
        for (Jogador jogador : leva) {
            double lucro = jogador.getSaldo() - jogador.getSaldoInicial();
            if (melhoresJogadores.size() < 3 || lucro > maiorLucro) {
                melhoresJogadores.add(jogador);
                maiorLucro = lucro;
            }
        }

        saldoFinalMesa = 0.0;
        for (Jogador jogador : leva) {
            saldoFinalMesa += jogador.getSaldo();
        }
    }

    private byte[] encriptar(String json) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO_ENCRIPTACAO);
        cipher.init(Cipher.ENCRYPT_MODE, chavePublica);
        return cipher.doFinal(json.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void enviarDadosMesa() {
        try {
            if (melhoresJogadores.isEmpty()) {
                System.err.println("[MESA " + mesaId + "] Nenhum jogador qualificado para envio.");
                return;
            }

            MesaResultadoDTO resultado = new MesaResultadoDTO(mesaId, saldoFinalMesa, new ArrayList<>(melhoresJogadores));
            String json = new Gson().toJson(resultado);
            byte[] dadosEncriptados = encriptar(json);
            String dadosBase64 = Base64.getEncoder().encodeToString(dadosEncriptados);

            URL url = new URL(URL_MESA + "?dados=" + dadosBase64);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

            int responseCode = conexao.getResponseCode();
            System.out.println("[MESA " + mesaId + "] Resposta do servidor: " + responseCode);
            conexao.disconnect();
        } catch (Exception e) {
            System.err.println("[MESA " + mesaId + "] Erro ao enviar dados.");
            e.printStackTrace();
        }
    }
}
