package br.edu.ifba.pacientes.cliente.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.edu.ifba.pacientes.cliente.comunicacao.Comunicacao;

public class comunicacaoimpl implements Comunicacao {

    private static final String URL_SERVIDOR = "http://localhost:8080/";

    private static final String URL_BIOMETRIA = URL_SERVIDOR + "biometria/";

    @SuppressWarnings("deprecation")
    @Override
    public String enviar(String id, int batimentos, int temperatura) throws Exception {
        URL url = new URL(URL_BIOMETRIA + id + "/" + batimentos + "/" + temperatura);

        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");

        if(conexao.getResponseCode() != 200) {
            throw new Exception("Servidor de biometria não encontrado");
        }

        InputStreamReader in = new InputStreamReader(conexao.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String resposta = br.readLine();

        conexao.disconnect();
        return resposta;
    }
    
}
