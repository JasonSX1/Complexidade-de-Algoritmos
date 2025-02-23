package src.br.edu.ifba.encriptacao.aleatoriedade;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import javax.sound.sampled.*;

import src.br.edu.ifba.encriptacao.excecoes.FalhaGeracaoDeChaves;

/**
 * Análise de Complexidade:  
 * - A complexidade geral desta classe varia de acordo com o tamanho do áudio.  
 * - Construtor GeradorDeAleatoriedadeReal(String caminhoAudio): O(1), abertura do arquivo de áudio.  
 * - Método nextInt() e nextLong(): O(t), onde t é o número de bytes lidos (até 4 para nextInt e 8 para nextLong).  
 * - Método extrairAleatoriedade(int tamanho): O(t), leitura de t bytes do fluxo de áudio.  
 * - Método finalizar(): O(1), fechamento do fluxo de áudio.  
 */


public class GeradorDeAleatoriedadeReal extends SecureRandom {
    private static final int TAMANHO_AMOSTRA = 1024; // Número de amostras lidas por vez
    private AudioInputStream audioStream;
    private byte[] buffer;

    public GeradorDeAleatoriedadeReal(String caminhoAudio) throws FalhaGeracaoDeChaves {
        try {
            File arquivoAudio = new File(caminhoAudio);
            audioStream = AudioSystem.getAudioInputStream(arquivoAudio);
            buffer = new byte[TAMANHO_AMOSTRA];
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new FalhaGeracaoDeChaves("Falha ao abrir arquivo de áudio: " + e.getMessage());
        }
    }

    @Override
    public int nextInt() {
        return extrairAleatoriedade(4);
    }

    @Override
    public long nextLong() {
        return (long) extrairAleatoriedade(8);
    }

    private int extrairAleatoriedade(int tamanho) {
        int valor = 0;
        try {
            int lidos = audioStream.read(buffer, 0, tamanho);
            if (lidos > 0) {
                for (int i = 0; i < lidos; i++) {
                    valor |= (buffer[i] & 0xFF) << (8 * i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valor;
    }

    public void finalizar() throws FalhaGeracaoDeChaves {
        try {
            if (audioStream != null) {
                audioStream.close();
            }
        } catch (IOException e) {
            throw new FalhaGeracaoDeChaves("Falha ao fechar o fluxo de áudio: " + e.getMessage());
        }
    }
}