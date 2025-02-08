package br.edu.ifba.cassino.servidor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

public class Desencriptador {
    private static final String ALGORITMO = "RSA";

    /**
     * 📌 Carrega a chave privada a partir do arquivo.
     */
    public static PrivateKey carregarChavePrivada(String caminhoChave) {
        try {
            byte[] chaveBytes = Files.readAllBytes(Paths.get(caminhoChave));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(chaveBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITMO);
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar chave privada: " + e.getMessage(), e);
        }
    }

    /**
     * 🔓 Descriptografa os dados usando a chave privada.
     */
    public static byte[] descriptografar(PrivateKey chavePrivada, byte[] dadosCriptografados) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, chavePrivada);
            return cipher.doFinal(dadosCriptografados);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar dados: " + e.getMessage(), e);
        }
    }
}
