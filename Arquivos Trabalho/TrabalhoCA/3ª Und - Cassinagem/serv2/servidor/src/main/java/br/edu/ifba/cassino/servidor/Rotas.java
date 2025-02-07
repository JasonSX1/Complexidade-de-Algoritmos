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
    
        // 🔹 O cliente já enviou o lucro total da mesa corretamente, então só exibimos
        double lucroTotalMesa = resultado.getSaldoFinalMesa();
    
        // 🔹 Sincronização para evitar sobreposição de prints
        synchronized (System.out) {
            System.out.println("\n===============================================");
            System.out.printf(" Dados recebidos da MESA %s%n", resultado.getMesaId());
            System.out.println("===============================================");
            System.out.printf(" Lucro do cassino: %.2f\n", -lucroTotalMesa);
            System.out.println("-----------------------------------------------");
            System.out.println(" Melhores jogadores:");
            System.out.println(" ID |    Nome Completo      | Saldo Inicial |  Saldo Final |   Lucro  ");
            System.out.println("----|----------------------|---------------|--------------|----------");
    
            resultado.getMelhoresJogadores().forEach(jogador -> {
                double lucroJogador = jogador.getSaldo() - jogador.getSaldoInicial();
                System.out.printf(" %2d | %-20s | %13.2f | %13.2f | %8.2f%n",
                        jogador.getId(), formatarNome(jogador.getNomeCompleto()),
                        jogador.getSaldoInicial(), jogador.getSaldo(), lucroJogador);
            });
    
            System.out.println("===============================================\n");
        }
    
        operacoes.processarJogadores(resultado.getMelhoresJogadores());
    
        return Response.ok("Dados da mesa " + resultado.getMesaId() + " recebidos com sucesso!").build();
    }
    
    /**
     * 🔹 Formata o nome para não ultrapassar um limite de 20 caracteres
     */
    private String formatarNome(String nome) {
        return nome.length() > 20 ? nome.substring(0, 17) + "..." : nome;
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