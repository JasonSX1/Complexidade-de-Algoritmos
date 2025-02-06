package br.edu.ifba.cassino.servidor;

import br.edu.ifba.cassino.servidor.impl.OperacoesImpl;
import br.edu.ifba.cassino.servidor.modelo.Jogador;
import br.edu.ifba.cassino.servidor.operacoes.Operacoes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/cassino")
public class Rotas {
    private static final Operacoes operacoes = new OperacoesImpl();
    private static final Gson gson = new Gson(); // Objeto Gson para processar JSON

    @POST
    @Path("/jogadores")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response receberJogadores(String json) {
        try {
            System.out.println("[DEBUG] JSON recebido: " + json);

            // Converte manualmente a String JSON para uma lista de jogadores
            List<Jogador> jogadores = gson.fromJson(json, new TypeToken<List<Jogador>>(){}.getType());

            System.out.println("[DEBUG] Lista de jogadores processada: " + jogadores);
            operacoes.processarJogadores(jogadores);

            return Response.ok("Jogadores processados com sucesso.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Erro ao processar jogadores: " + e.getMessage()).build();
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
}
