package br.edu.ifba.cassino.cliente.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Encriptador {

    private static final String RSA_ALGORITHM = "RSA";
    private static final String AES_ALGORITHM = "AES";

    // 🔹 Método para gerar uma chave AES aleatória de 256 bits
    public static SecretKey gerarChaveAES() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(256, new SecureRandom());
        return keyGenerator.generateKey();
    }

    // 🔹 Método para criptografar os dados usando AES
    public static byte[] criptografarAES(SecretKey chaveAES, byte[] dados) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, chaveAES);
        return cipher.doFinal(dados);
    }

    // 🔹 Método para criptografar a chave AES usando RSA
    public static byte[] criptografarChaveAES(PublicKey chavePublica, SecretKey chaveAES) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, chavePublica);
        return cipher.doFinal(chaveAES.getEncoded());
    }

    // 🔹 Método para encriptar os dados de forma híbrida (AES + RSA)
    public static String encriptar(PublicKey chavePublica, byte[] dados) throws Exception {
        // Gerar chave AES aleatória
        SecretKey chaveAES = gerarChaveAES();

        // Criptografar os dados usando AES
        byte[] dadosCriptografados = criptografarAES(chaveAES, dados);

        // Criptografar a chave AES usando RSA
        byte[] chaveCriptografada = criptografarChaveAES(chavePublica, chaveAES);

        // Converter para Base64 e concatenar chave e dados criptografados
        String chaveBase64 = Base64.getEncoder().encodeToString(chaveCriptografada);
        String dadosBase64 = Base64.getEncoder().encodeToString(dadosCriptografados);

        return chaveBase64 + ":" + dadosBase64;
    }
}
