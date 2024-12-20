
package br.edu.ifba.avaliacao.servidor;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Servidor {

    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {
        // Configura o servidor para utilizar os pacotes do projeto
        final ResourceConfig rc = new ResourceConfig().packages("br.edu.ifba.cassino.servidor");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println("Servidor iniciado em " + BASE_URI);
        System.out.println("Aguardando dados dos clientes... Pressione ENTER para encerrar.");
        System.in.read();
        server.shutdownNow();
        System.out.println("Servidor encerrado.");
    }
}
