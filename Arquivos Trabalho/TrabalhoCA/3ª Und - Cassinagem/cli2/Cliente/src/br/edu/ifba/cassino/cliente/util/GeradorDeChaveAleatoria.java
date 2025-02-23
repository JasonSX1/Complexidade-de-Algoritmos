package br.edu.ifba.cassino.cliente.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GeradorDeChaveAleatoria {

    private static final int TAMANHO_CHAVE = 16; // 16 bytes (128 bits)
    private static final int TAMANHO_AMOSTRA = 1024; // Número de bytes lidos do áudio

    /*
     * Complexidade geral da classe: O(1)
     * Todas as operações realizadas são baseadas em tamanhos de buffers constantes
     * (TAMANHO_CHAVE e TAMANHO_AMOSTRA),
     * ou seja, o tempo de execução não varia de acordo com o tamanho do arquivo de
     * áudio, mas sim com valores fixos.
     * Como resultado, a complexidade geral do método e da classe como um todo é
     * O(1).
     */

    /**
     * Gera uma chave aleatória a partir de um arquivo de áudio
     * @param caminhoAudio Caminho do arquivo de áudio
     * @return Chave aleatória em bytes
     * @throws Exception Se ocorrer erro ao processar o áudio
     */
    public static byte[] gerarChaveAleatoria(String caminhoAudio) throws Exception {
        File arquivoAudio = new File(caminhoAudio);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivoAudio);

        byte[] buffer = new byte[TAMANHO_AMOSTRA];
        int bytesLidos = audioStream.read(buffer, 0, TAMANHO_AMOSTRA);
        audioStream.close();

        if (bytesLidos < TAMANHO_CHAVE) {
            throw new IOException("Arquivo de áudio muito curto para gerar chave aleatória.");
        }

        // Usa os primeiros bytes para gerar uma chave aleatória
        byte[] chaveAleatoria = new byte[TAMANHO_CHAVE];
        System.arraycopy(buffer, 0, chaveAleatoria, 0, TAMANHO_CHAVE);

        return chaveAleatoria;
    }
}
