package br.edu.ifba.cassino.cliente.impl;

import br.edu.ifba.cassino.cliente.comunicacao.Cliente;
import br.edu.ifba.cassino.cliente.modelo.Jogador;
import br.edu.ifba.cassino.cliente.modelo.MesaResultadoDTO;
import com.google.gson.Gson;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
    private double saldoFinalMesa;

    public ClienteImpl(String mesaId, int totalJogadores, int jogadoresPorLeva) {
        this.mesaId = mesaId;
        this.totalJogadores = totalJogadores;
        this.jogadoresPorLeva = jogadoresPorLeva;
        this.jogadores = new ArrayList<>();
        this.melhoresJogadores = new LinkedList<>();
        this.saldoFinalMesa = 0.0;
    }

    @Override
    public void configurar(int totalJogadores, int jogadoresPorLeva) {
        System.out.println("[DEBUG] Chamando configurar() com " + totalJogadores + " jogadores...");
    
        this.jogadores = Jogador.gerarJogadores(totalJogadores);
        
        System.out.println("[DEBUG] Jogadores armazenados na lista após gerarJogadores(): " + jogadores.size());
    }
    

    @Override
    public void iniciar() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("[MESA " + mesaId + "] Iniciando apostas...");

        if (jogadores.size() != totalJogadores) {
            System.err.println("[ERRO] Quantidade de jogadores incorreta! Esperado: " + totalJogadores + ", Obtido: " + jogadores.size());
            return;
        }

        for (int i = 0; i < totalJogadores; i += jogadoresPorLeva) {
            if (i >= jogadores.size()) {
                System.err.println("[ERRO] Tentativa de acessar subList além do tamanho da lista. Encerrando loop.");
                break;
            }

            int fim = Math.min(i + jogadoresPorLeva, jogadores.size());
            List<Jogador> levaJogadores = new ArrayList<>(jogadores.subList(i, fim));

            System.out.println("[DEBUG] Processando jogadores de índice " + i + " até " + (fim - 1));

            levaJogadores.forEach(Jogador::apostar);
            atualizarMelhoresJogadores(levaJogadores);
            enviarDadosMesa();
        }
    }

    private void atualizarMelhoresJogadores(List<Jogador> leva) {
        melhoresJogadores = new LinkedList<>();
        double maiorLucro = Double.NEGATIVE_INFINITY;
        int jogadoresConsiderados = 0;

        for (Jogador jogador : leva) {
            double lucro = jogador.getSaldo() - jogador.getSaldoInicial();
            if (jogadoresConsiderados < 3) {
                if (melhoresJogadores.isEmpty() || lucro > maiorLucro) {
                    melhoresJogadores.add(jogador);
                    maiorLucro = lucro;
                }
                jogadoresConsiderados++;
            }
        }

        saldoFinalMesa = 0.0;
        for (Jogador jogador : leva) {
            saldoFinalMesa += jogador.getSaldo();
        }
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

            URL url = new URL(URL_MESA);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            conexao.setDoOutput(true);
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json");

            try (OutputStream os = conexao.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
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
