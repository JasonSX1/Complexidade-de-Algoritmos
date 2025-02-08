package src.br.edu.ifba.encriptacao;

import java.security.KeyPair;

import src.br.edu.ifba.encriptacao.aleatoriedade.GeradorDeAleatoriedadeReal;
import src.br.edu.ifba.encriptacao.chaves.GeradorDeChaves;
import src.br.edu.ifba.encriptacao.excecoes.FalhaEncriptacao;
import src.br.edu.ifba.encriptacao.excecoes.FalhaGeracaoDeChaves;
import src.br.edu.ifba.encriptacao.impl.EncriptadorImpl;
import src.br.edu.ifba.encriptacao.impl.GeradorDeChavesImpl;

public class App {

    private static final String CAMINHO_DO_AUDIO = "fonteAleatoriedade\\AudioCassino.wav";
    private static final String ALGORITMO_DE_ENCRIPTACAO = "RSA";

    private static final String CAMINHO_CHAVE_PRIVADA = "chave_privada.key";
    private static final String CAMINHO_CHAVE_PUBLICA = "chave_publica.key";

    public static void main(String[] args) {
        try {
            System.out.println("Inicializando encriptação baseada em áudio...");

            // 🔹 Inicializa o gerador de chaves com aleatoriedade vinda do áudio
            GeradorDeChaves<GeradorDeAleatoriedadeReal> geradorDeChaves = new GeradorDeChavesImpl();
            geradorDeChaves.inicializar(new GeradorDeAleatoriedadeReal(CAMINHO_DO_AUDIO), ALGORITMO_DE_ENCRIPTACAO);

            System.out.println("Gerando e testando 10 pares de chaves RSA...");

            for (int i = 0; i < 10; i++) {
                System.out.println("******* Gerando par de chaves #" + (i + 1) + " *******");
                KeyPair chaves = geradorDeChaves.gerarChaves();

                // Inicializa o encriptador com as chaves geradas
                EncriptadorImpl encriptador = new EncriptadorImpl(CAMINHO_DO_AUDIO);

                // Mensagem de teste
                String mensagem = "Complexidade de Algoritmos";
                System.out.println("📩 Mensagem original: " + mensagem);

                // Encriptando a mensagem
                byte[] mensagemCriptografada = encriptador.encriptar(mensagem.getBytes());
                System.out.println("Encriptado: " + new String(mensagemCriptografada));

                // 🔹 Descriptografando a mensagem
                byte[] mensagemDescriptografada = encriptador.descriptografar(mensagemCriptografada);
                System.out.println("Desencriptado: " + new String(mensagemDescriptografada));
            }

            // Gerar e salvar as chaves
            geradorDeChaves.gerarChaves(CAMINHO_CHAVE_PRIVADA, CAMINHO_CHAVE_PUBLICA);
            geradorDeChaves.finalizar();

            System.out.println("Processo de encriptação finalizado com sucesso!");
        } catch (FalhaGeracaoDeChaves | FalhaEncriptacao e) {
            System.err.println("Erro no processo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
