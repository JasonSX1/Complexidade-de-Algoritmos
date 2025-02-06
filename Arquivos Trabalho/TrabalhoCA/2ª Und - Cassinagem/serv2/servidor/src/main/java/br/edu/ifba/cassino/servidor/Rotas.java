// Classe responsável por definir as rotas do Servidor e garantir concorrência
package br.edu.ifba.cassino.servidor;

import br.edu.ifba.cassino.servidor.impl.OperacoesImpl;
import br.edu.ifba.cassino.servidor.modelo.Jogador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Path("/cassino")
public class Rotas {
    private static final OperacoesImpl operacoes = new OperacoesImpl();
    private static final Lock lock = new ReentrantLock(); // Garantia de segurança em concorrência

    @POST
    @Path("/jogadores")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receberJogadores(List<Jogador> jogadores) {
        lock.lock(); // Garante que múltiplas requisições não corrompam os dados
        try {
            if (jogadores == null || jogadores.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Lista de jogadores vazia.").build();
            }
            operacoes.processarJogadores(jogadores);
            return Response.ok().build();
        } finally {
            lock.unlock(); // Libera o bloqueio após a operação
        }
    }

    @GET
    @Path("/melhores")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterMelhoresJogadores() {
        List<Jogador> melhores = operacoes.getMelhoresJogadores();
        return Response.ok(melhores).build();
    }

    @GET
    @Path("/info")
    @Produces(MediaType.TEXT_PLAIN)
    public String getInfo() {
        return "API de gerenciamento de apostas do cassino, versão 1.0";
    }

    @GET
    @Path("/raiz")
    @Produces(MediaType.TEXT_PLAIN)
    public String raiz() {
        return "Servidor funcionando!";
    }
}
