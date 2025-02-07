package br.edu.ifba.cassino.cliente.impl;

import br.edu.ifba.cassino.cliente.comunicacao.Cliente;
import br.edu.ifba.cassino.cliente.modelo.Jogador;
import br.edu.ifba.cassino.cliente.modelo.MesaResultadoDTO;
import br.edu.ifba.cassino.cliente.sensoriamento.SensorDeApostas;

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

            SensorDeApostas.gerarApostasParaJogadores(levaJogadores, 5);

            levaJogadores.forEach(Jogador::apostar);
            atualizarMelhoresJogadores(levaJogadores);
            enviarDadosMesa();
        }
    }

    private void atualizarMelhoresJogadores(List<Jogador> leva) {
        // 🔹 Garante que sempre tentamos pegar os três melhores
        if (leva.size() < 3) {
            System.err.println("[ERRO] Menos de 3 jogadores na leva. Enviando todos disponíveis.");
            melhoresJogadores = new LinkedList<>(leva);
            return;
        }
    
        melhoresJogadores = new LinkedList<>();
        Jogador primeiro = null, segundo = null, terceiro = null;
    
        for (Jogador jogador : leva) {
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
    
        // 🔹 Garante que os três melhores são enviados
        if (primeiro != null) melhoresJogadores.add(primeiro);
        if (segundo != null) melhoresJogadores.add(segundo);
        if (terceiro != null) melhoresJogadores.add(terceiro);
    
        // 🔹 Exibe quem foi selecionado
        System.out.println("[DEBUG] Melhores jogadores selecionados para envio:");
        for (Jogador j : melhoresJogadores) {
            System.out.printf("[DEBUG] %s | Saldo Inicial: %.2f | Saldo Final: %.2f\n",
                    j.getNomeCompleto(), j.getSaldoInicial(), j.getSaldo());
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
    
            // 🔹 Exibir os dados antes de enviar
            System.out.println("===============================================");
            System.out.println(" Dados enviados ao SERVIDOR pela MESA " + mesaId);
            System.out.println("===============================================");
            System.out.println(" ID |    Nome     | Saldo Inicial |  Saldo Final");
            System.out.println("----|-------------|---------------|--------------");
    
            for (Jogador jogador : melhoresJogadores) {
                System.out.printf(" %2d | %-12s | %13.2f | %13.2f\n",
                        jogador.getId(), jogador.getNomeCompleto(), jogador.getSaldoInicial(), jogador.getSaldo());
            }
    
            System.out.println("===============================================");
    
            // 🔹 Criando o DTO para envio
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
