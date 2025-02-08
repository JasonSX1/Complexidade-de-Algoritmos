package br.edu.ifba.cassino.servidor;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/*
 * Complexidade geral da classe: O(1)
 * O método startServer() realiza a configuração do servidor e inicializa o Grizzly, o que ocorre em tempo constante O(1), pois não há loops dependentes da entrada.
 * O método main() executa a inicialização do servidor (O(1)), exibe mensagens (O(1)) e aguarda a entrada do usuário (O(1)), resultando em complexidade constante.
 * Como todas as operações ocorrem em tempo fixo, a complexidade geral da classe é O(1).
 */

public class Servidor {
    
    private static final String HOST = "http://localhost:";
    private static final int PORT = 8080;
    public static final String BASE_URI = HOST + PORT + "/";

    public static HttpServer startServer() {
        System.out.println("[SERVIDOR] Configurando servidor...");

        final ResourceConfig rc = new ResourceConfig().packages("br.edu.ifba.cassino.servidor");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) {
        try {
            final HttpServer server = startServer();
            System.out.println("[SERVIDOR] Servidor iniciado em " + BASE_URI);
            System.out.println("[SERVIDOR] Aguardando dados das mesas...");
            System.out.println("[SERVIDOR] Pressione ENTER para encerrar.");

            System.in.read();
            server.shutdownNow();
            System.out.println("[SERVIDOR] Servidor encerrado.");
        } catch (IOException e) {
            System.err.println("[ERRO] Falha ao iniciar o servidor: " + e.getMessage());
        }
    }
}