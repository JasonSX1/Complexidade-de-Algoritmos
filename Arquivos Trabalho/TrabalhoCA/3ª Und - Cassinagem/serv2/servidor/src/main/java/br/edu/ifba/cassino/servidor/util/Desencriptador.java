package br.edu.ifba.cassino.servidor.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class Desencriptador {
    
    private static final String RSA_ALGORITHM = "RSA";
    private static final String AES_ALGORITHM = "AES";

    // 🔹 Método para carregar a chave privada
    public static PrivateKey carregarChavePrivada(String caminhoChave) throws Exception {
        byte[] bytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(caminhoChave));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance(RSA_ALGORITHM);
        return kf.generatePrivate(spec);
    }

    // 🔹 Método para descriptografar a chave AES usando RSA
    public static SecretKey descriptografarChaveAES(PrivateKey chavePrivada, byte[] chaveCriptografada) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, chavePrivada);
        byte[] chaveAESBytes = cipher.doFinal(chaveCriptografada);
        return new SecretKeySpec(chaveAESBytes, AES_ALGORITHM);
    }

    // 🔹 Método para descriptografar os dados usando AES
    public static byte[] descriptografarAES(SecretKey chaveAES, byte[] dadosCriptografados) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, chaveAES);
        return cipher.doFinal(dadosCriptografados);
    }

    // 🔹 Método para descriptografar os dados recebidos (AES + RSA)
    public static String descriptografar(PrivateKey chavePrivada, String dadosRecebidos) throws Exception {
        // Separar chave AES criptografada dos dados criptografados
        String[] partes = dadosRecebidos.split(":");
        byte[] chaveCriptografada = Base64.getDecoder().decode(partes[0]);
        byte[] dadosCriptografados = Base64.getDecoder().decode(partes[1]);

        // Descriptografar chave AES
        SecretKey chaveAES = descriptografarChaveAES(chavePrivada, chaveCriptografada);

        // Descriptografar os dados usando AES
        byte[] dadosDescriptografados = descriptografarAES(chaveAES, dadosCriptografados);

        return new String(dadosDescriptografados, java.nio.charset.StandardCharsets.UTF_8);
    }
}
