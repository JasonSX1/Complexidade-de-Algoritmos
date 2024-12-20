
package br.edu.ifba.avaliacao.cassino;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class ServidorCassino {

    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {

        final ResourceConfig rc = new ResourceConfig().packages("br.edu.ifba.avaliacao.cassino");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println("Aguardando processamento de mesas de cassino...");
        System.in.read();
        server.shutdownNow();
    }
}
