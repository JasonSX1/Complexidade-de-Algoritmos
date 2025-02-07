package br.edu.ifba.cassino.servidor;

import br.edu.ifba.cassino.servidor.impl.OperacoesImpl;
import br.edu.ifba.cassino.servidor.operacoes.Operacoes;
import br.edu.ifba.cassino.servidor.modelo.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/cassino")
public class Rotas {
    private static final Operacoes operacoes = new OperacoesImpl();

    @POST
    @Path("/melhorGrupo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receberDadosMesa(MesaResultadoDTO resultado) {
        if (resultado == null || resultado.getMelhoresJogadores().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro: Dados da mesa estão vazios ou mal formatados.")
                    .build();
        }

        // Melhorando a visualização no console
        System.out.println("\n===============================================");
        System.out.printf(" Dados recebidos da MESA %s%n", resultado.getMesaId());
        System.out.println("===============================================");
        System.out.printf(" Saldo final da mesa: %.2f%n", resultado.getSaldoFinalMesa());
        System.out.println("-----------------------------------------------");
        System.out.println(" Melhores jogadores:");
        System.out.println(" ID |    Nome     | Saldo Inicial |  Saldo Final ");
        System.out.println("----|-------------|---------------|--------------");

        resultado.getMelhoresJogadores().forEach(jogador -> System.out.printf(" %2d | %-11s |  %12.2f |  %12.2f %n",
                jogador.getId(), jogador.getNome(),
                jogador.getSaldoInicial(), jogador.getSaldo()));

        System.out.println("===============================================\n");

        operacoes.processarJogadores(resultado.getMelhoresJogadores());

        return Response.ok("Dados da mesa " + resultado.getMesaId() + " recebidos com sucesso!").build();
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