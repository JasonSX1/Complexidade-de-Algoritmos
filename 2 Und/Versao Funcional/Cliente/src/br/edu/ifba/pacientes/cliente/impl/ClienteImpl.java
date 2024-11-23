package br.edu.ifba.pacientes.cliente.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import br.edu.ifba.pacientes.cliente.comunicacao.Cliente;
import br.edu.ifba.pacientes.cliente.modelo.Biometria;
import br.edu.ifba.pacientes.cliente.modelo.Paciente;
import br.edu.ifba.pacientes.cliente.sensoriamento.Sensoriamento;

public class ClienteImpl implements Cliente<Paciente, Biometria>, Runnable {

    private static final int TOTAL_DE_LEITURAS = 1000;

    private static final String URL_SERVIDOR = "http://localhost:8080/";
    private static final String URL_BIOMETRIA = URL_SERVIDOR + "biometria/";

    private static final int LIMIAR_OSCILACAO_BATIMENTOS = 5;
    private static final int LIMIAR_OSCILACAO_TEMPERATURA = 3;

    private static final Biometria ultimaLeitura = new Biometria(0, 0);

    private Paciente monitorado = null;
    private Sensoriamento<Biometria> sensoriamento = null;

    public void configurar(Paciente monitorado, Sensoriamento<Biometria> sensoriamento) {
        this.monitorado = monitorado;
        this.sensoriamento = sensoriamento;
    }

    @SuppressWarnings("deprecation")
    @Override
    public String enviar(Biometria sensor) throws Exception {
        URL url = new URL(URL_BIOMETRIA + monitorado.getIdentificacao() + "/" + sensor.getBatimentos() + "/"
                + sensor.getTemperatura());

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
            int diferencaBatimentos = Math.abs(leitura.getBatimentos() - ultimaLeitura.getBatimentos());
            int diferencaTemperatura = Math.abs(leitura.getTemperatura() - ultimaLeitura.getTemperatura());

            if (diferencaBatimentos >= LIMIAR_OSCILACAO_BATIMENTOS
                    || diferencaTemperatura >= LIMIAR_OSCILACAO_TEMPERATURA) {
                ultimaLeitura.setBatimentos(leitura.getBatimentos());
                ultimaLeitura.setTemperatura(leitura.getTemperatura());

                try {
                    String resposta = enviar(leitura);
                    System.out.println(resposta.equals("ok") ? "leitura enviada do paciente: " + monitorado
                            : "falha no envio da leitura");

                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Leitura ignorada, sem diferenças significativas");
            }
        }
    }

}
