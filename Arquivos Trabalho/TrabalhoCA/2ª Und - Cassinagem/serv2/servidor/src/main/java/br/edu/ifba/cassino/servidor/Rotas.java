package br.edu.ifba.cassino.servidor;

import java.util.List;
import jakarta.ws.rs.core.Response;
import br.edu.ifba.cassino.servidor.impl.OperacoesImpl;
import br.edu.ifba.cassino.servidor.modelo.Jogador;
import br.edu.ifba.cassino.servidor.modelo.JogadoresWrapper;
import br.edu.ifba.cassino.servidor.operacoes.Operacoes;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/cassino")
public class Rotas {
    private static final Operacoes operacoes = new OperacoesImpl();

    @POST
    @Path("/jogadores")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receberJogadores(JogadoresWrapper wrapper) {
        if (wrapper == null || wrapper.getJogadores() == null) {
            System.err.println("[ERRO] JSON recebido está nulo ou mal formatado.");
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Erro: JSON enviado está mal formatado ou vazio.")
                           .build();
        }
    
        System.out.println("[DEBUG] JSON recebido: " + wrapper.getJogadores());
        operacoes.processarJogadores(wrapper.getJogadores());
        return Response.ok().build();
    }    
    
    // @GET
    // @Path("jogadores")
    // @Produces(MediaType.APPLICATION_JSON)
    // public List<Jogador> listarJogadores() {
    //     return operacoes.listarJogadores();
    // }

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

    // comentado para evitar conflito com a rota /jogador - esse daqui é só pra dados nao serializados
    // @GET
    // @Path("jogador")
    // @Produces(MediaType.TEXT_PLAIN)
    // public String gravarDadosJogador(
    //         @QueryParam("id") int id,
    //         @QueryParam("nome") String nome,
    //         @QueryParam("saldoInicial") double saldoInicial,
    //         @QueryParam("saldoFinal") double saldoFinal,
    //         @QueryParam("totalApostas") int totalApostas) {
    //     System.out.println("Jogador recebido:");
    //     System.out.println("ID: " + id);
    //     System.out.println("Nome: " + nome);
    //     System.out.println("Saldo Inicial: " + saldoInicial);
    //     System.out.println("Saldo Final: " + saldoFinal);
    //     System.out.println("Total de Apostas: " + totalApostas);
    //     return "ok";
    // }
}
