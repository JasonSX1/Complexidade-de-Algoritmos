package br.edu.ifba.pacientes.cliente.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.github.javafaker.HitchhikersGuideToTheGalaxy;

import br.edu.ifba.pacientes.cliente.comunicacao.Cliente;
import br.edu.ifba.pacientes.cliente.modelo.Biometria;
import br.edu.ifba.pacientes.cliente.modelo.Paciente;
import br.edu.ifba.pacientes.cliente.sensoriamento.Sensoriamento;

public class ClienteImpl implements Cliente<Paciente, Biometria>, Runnable {

    private static final int TOTAL_DE_LEITURAS = 1000;

    private static final String URL_SERVIDOR = "http://localhost:8080/";
    private static final String URL_BIOMETRIA = URL_SERVIDOR + "biometria/";

    private static final int LIMIAR_OSCILACAO_DE_BATIMENTOS = 5;
    private static final int LIMIAR_OSCILACAO_DE_TEMPERATURA = 3;

    private List<Biometria> padrao = new ArrayList<>();
    private Queue<Biometria> historicoDeLeituras = new LinkedList<>();
    private Biometria ultimaLeitura = new Biometria(0, 0);

    private static final int TAMANHO_MAXIMO_HISTORICO = 50;

    private Paciente monitorado = null;
    private Sensoriamento<Biometria> sensoriamento = null;

    public void configurar(Paciente monitorado, Sensoriamento<Biometria> sensoriamento, List<Biometria> padrao) {
        this.monitorado = monitorado;
        this.sensoriamento = sensoriamento;
        this.padrao = padrao;
    }

    @SuppressWarnings("deprecation")
    @Override
    public String enviar(Biometria sensor) throws Exception {

        int emergencias = detectarEmergencias();

        URL url = new URL(URL_BIOMETRIA + monitorado.getIdentificacao() + "/"
                + URLEncoder.encode(monitorado.getNome(), StandardCharsets.UTF_8.toString()) + "/"
                + sensor.getBatimentos() + "/"
                + sensor.getTemperatura() + "/" + emergencias);

        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        if (conexao.getResponseCode() != 200) {
            throw new Exception("servidor de biometria não encontrado");
        }

        InputStreamReader in = new InputStreamReader(conexao.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String resposta = br.readLine();

        conexao.disconnect();

        return resposta;
    }

    @Override
    public void run() {
        List<Biometria> leituras = sensoriamento.gerar(TOTAL_DE_LEITURAS);

        for (Biometria leitura : leituras) {
            if (Math.abs(leitura.getBatimentos() - ultimaLeitura.getBatimentos()) > LIMIAR_OSCILACAO_DE_BATIMENTOS
                    || Math.abs(leitura.getTemperatura()
                            - ultimaLeitura.getTemperatura()) > LIMIAR_OSCILACAO_DE_TEMPERATURA) {

                ultimaLeitura = leitura;

                historicoDeLeituras.add(ultimaLeitura);
                if (historicoDeLeituras.size() > TAMANHO_MAXIMO_HISTORICO) {
                    historicoDeLeituras.remove();
                }

                try {
                    String resposta = enviar(leitura);
                    System.out.println(resposta.equals("ok")
                            ? "leitura enviada do paciente: " + monitorado
                            : "falha no envio da leitura");

                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("leitura ignorada, não ocorreu oscilação significativa");
            }
        }
    }

    // Complexidade O(N^2)

    @Override
    public int detectarEmergencias() {
        int totalDeDeteccoes = 0;

        List<Biometria> historico = new ArrayList<>(historicoDeLeituras);

        inicioPesquisaPorPadrao:
            for (int i = 0; i < historico.size() - padrao.size(); i++) {
                for (int j = 0; j < padrao.size(); j++) {
                    if (historico.get(i + j).getBatimentos() == padrao.get(j).getBatimentos()) {
                        totalDeDeteccoes++;

                        if (totalDeDeteccoes == padrao.size()) {
                            break inicioPesquisaPorPadrao;
                        } 
                    } else {
                        totalDeDeteccoes = 0;

                        break;
                    }
                }
            }

    return totalDeDeteccoes;
}
}
