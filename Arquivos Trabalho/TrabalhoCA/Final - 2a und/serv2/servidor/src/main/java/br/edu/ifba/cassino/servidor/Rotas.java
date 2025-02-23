package br.edu.ifba.cassino.servidor;

import br.edu.ifba.cassino.servidor.impl.OperacoesImpl;
import br.edu.ifba.cassino.servidor.operacoes.Operacoes;
import br.edu.ifba.cassino.servidor.modelo.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/*
 * Complexidade das funções:
 * 
 *   - Converter JSON para objeto: O(N), onde N é o tamanho do JSON.
 *   - Iterar sobre a lista de jogadores recebidos: O(M), onde M é o número de jogadores.
 *   - Adicionar jogadores à lista e exibir dados: O(M).
 *   - No pior caso, a complexidade total é O(N + M), que pode ser aproximada para O(N).
 * 
 * - getInfo():
 *   - Retorna uma string fixa em O(1).
 * 
 * Portanto, a função principal receberDadosMesa é O(N), enquanto getInfo é O(1).
 */

@Path("/cassino")
public class Rotas {
    private static final Operacoes operacoes = new OperacoesImpl();

    @POST
    @Path("/melhorGrupo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receberDadosMesa(MesaResultadoDTO resultado) {
        
        // Verifica se os dados da mesa são válidos
        if (resultado == null || resultado.getMelhoresJogadores().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro: Dados da mesa estão vazios ou mal formatados.")
                    .build();
        }
    
        // Sincronização para evitar sobreposição de prints
        synchronized (System.out) {
            System.out.println("\n===============================================");
            System.out.printf(" Dados recebidos da MESA %s%n", resultado.getMesaId());
            System.out.println("===============================================");
            System.out.printf(" Lucro total da mesa: %.2f\n", resultado.getSaldoFinalMesa());
            System.out.println("-----------------------------------------------");
            System.out.println(" Melhores jogadores:");
            System.out.println(" ID |    Nome Completo      | Saldo Inicial |  Saldo Final |   Lucro  ");
            for (Jogador jogador : resultado.getMelhoresJogadores()) {
                double lucroJogador = jogador.getSaldo() - jogador.getSaldoInicial();
                System.out.printf(" %2d | %-20s | %13.2f | %13.2f | %8.2f%n",
                        jogador.getId(), jogador.getNomeCompleto(),
                        jogador.getSaldoInicial(), jogador.getSaldo(), lucroJogador);
            }
            System.out.println("===============================================");
        }
    
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