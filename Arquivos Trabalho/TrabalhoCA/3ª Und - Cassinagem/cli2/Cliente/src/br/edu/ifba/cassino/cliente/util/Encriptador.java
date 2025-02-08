package br.edu.ifba.cassino.cliente.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;

// A complexidade geral da classe é O(n), pois a complexidade dos métodos depende do tamanho dos dados.

public class Encriptador {

    private static final String RSA_ALGORITHM = "RSA";
    private static final String AES_ALGORITHM = "AES";

    /**
     * Gera uma chave AES aleatória de 256 bits.
     * Complexidade: **O(1)** (operações fixas de geração de chave)
     */
    public static SecretKey gerarChaveAES() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(256, new SecureRandom()); // O(1)
        return keyGenerator.generateKey(); // O(1)
    }

    /**
     * Criptografa os dados usando AES.
     * Complexidade: **O(n)** (criptografia depende do tamanho dos dados)
     */
    public static byte[] criptografarAES(SecretKey chaveAES, byte[] dados) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, chaveAES); // O(1)
        return cipher.doFinal(dados); // O(n) - Depende do tamanho dos dados
    }

    /**
     * Criptografa a chave AES usando RSA.
     * Complexidade: **O(1)** (tamanho da chave é fixo, operação de criptografia é constante)
     */
    public static byte[] criptografarChaveAES(PublicKey chavePublica, SecretKey chaveAES) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, chavePublica); // O(1)
        return cipher.doFinal(chaveAES.getEncoded()); // O(1)
    }

    /**
     * Método para encriptar os dados de forma híbrida (AES + RSA).
     * Complexidade: **O(n)** (criptografia dos dados é proporcional ao tamanho dos dados)
     */
    public static String encriptar(PublicKey chavePublica, byte[] dados) throws Exception {
        // Gerar chave AES aleatória (O(1))
        SecretKey chaveAES = gerarChaveAES();

        // Criptografar os dados usando AES (O(n))
        byte[] dadosCriptografados = criptografarAES(chaveAES, dados);

        // Criptografar a chave AES usando RSA (O(1))
        byte[] chaveCriptografada = criptografarChaveAES(chavePublica, chaveAES);

        // Converter para Base64 e concatenar chave e dados criptografados (O(n))
        String chaveBase64 = Base64.getEncoder().encodeToString(chaveCriptografada); // O(1)
        String dadosBase64 = Base64.getEncoder().encodeToString(dadosCriptografados); // O(n)

        return chaveBase64 + ":" + dadosBase64; // O(n)
    }
}
