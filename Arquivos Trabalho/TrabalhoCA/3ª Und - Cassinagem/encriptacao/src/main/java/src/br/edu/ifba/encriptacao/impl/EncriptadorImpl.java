package src.br.edu.ifba.encriptacao.impl;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

import src.br.edu.ifba.encriptacao.aleatoriedade.GeradorDeAleatoriedadeReal;
import src.br.edu.ifba.encriptacao.chaves.GeradorDeChaves;
import src.br.edu.ifba.encriptacao.excecoes.FalhaEncriptacao;
import src.br.edu.ifba.encriptacao.excecoes.FalhaGeracaoDeChaves;

public class EncriptadorImpl {
    private Cipher cipher;
    private PublicKey chavePublica;
    private PrivateKey chavePrivada;
    private GeradorDeChavesImpl geradorDeChaves;

    public EncriptadorImpl(String caminhoAudio) throws FalhaGeracaoDeChaves {
        try {
            // Inicializa o gerador de chaves baseado em áudio
            GeradorDeAleatoriedadeReal geradorAleatorio = new GeradorDeAleatoriedadeReal(caminhoAudio);
            this.geradorDeChaves = new GeradorDeChavesImpl();
            this.geradorDeChaves.inicializar(geradorAleatorio, "RSA");

            // Gera chaves e as armazena em arquivos
            this.geradorDeChaves.gerarChaves("chave_privada.key", "chave_publica.key");

            // Carrega as chaves geradas
            carregarChaves("chave_privada.key", "chave_publica.key");

            cipher = Cipher.getInstance("RSA");
        } catch (Exception e) {
            throw new FalhaGeracaoDeChaves("Erro ao inicializar encriptador: " + e.getMessage());
        }
    }

    private void carregarChaves(String arquivoPrivado, String arquivoPublico) throws Exception {
        File arquivoChavePrivada = new File(arquivoPrivado);
        File arquivoChavePublica = new File(arquivoPublico);

        if (!arquivoChavePrivada.exists() || !arquivoChavePublica.exists()) {
            throw new FalhaGeracaoDeChaves("Arquivos de chave não encontrados.");
        }

        // Carregar chave privada
        byte[] chavePrivBytes = Files.readAllBytes(arquivoChavePrivada.toPath());
        PKCS8EncodedKeySpec chavePrivSpec = new PKCS8EncodedKeySpec(chavePrivBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.chavePrivada = keyFactory.generatePrivate(chavePrivSpec);

        // Carregar chave pública
        byte[] chavePubBytes = Files.readAllBytes(arquivoChavePublica.toPath());
        X509EncodedKeySpec chavePubSpec = new X509EncodedKeySpec(chavePubBytes);
        this.chavePublica = keyFactory.generatePublic(chavePubSpec);
    }

    public byte[] encriptar(byte[] dados) throws FalhaEncriptacao {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, chavePublica);
            return cipher.doFinal(dados);
        } catch (Exception e) {
            throw new FalhaEncriptacao("Erro ao encriptar dados: " + e.getMessage());
        }
    }

    public byte[] descriptografar(byte[] dadosCriptografados) throws FalhaEncriptacao {
        try {
            cipher.init(Cipher.DECRYPT_MODE, chavePrivada);
            return cipher.doFinal(dadosCriptografados);
        } catch (Exception e) {
            throw new FalhaEncriptacao("Erro ao descriptografar dados: " + e.getMessage());
        }
    }
}
