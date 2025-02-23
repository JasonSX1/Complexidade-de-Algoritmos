package br.edu.ifba.cassino.servidor;

import br.edu.ifba.cassino.servidor.impl.OperacoesImpl;
import br.edu.ifba.cassino.servidor.operacoes.Operacoes;
import br.edu.ifba.cassino.servidor.util.Desencriptador;
import br.edu.ifba.cassino.servidor.modelo.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.security.PrivateKey;
import java.util.Base64;
import com.google.gson.Gson;

/*
 * Complexidade das funções:
 * 
 * - receberDadosMesa(String dadosCriptografados):
 *   - Descriptografar os dados: O(N), onde N é o tamanho da string criptografada.
 *   - Converter JSON para objeto: O(M), onde M é o tamanho do JSON.
 *   - Iterar sobre a lista de jogadores recebidos: O(K), onde K é o número de jogadores.
 *   - Adicionar jogadores à lista e exibir dados: O(K).
 *   - No pior caso, a complexidade total é O(N + M + K), que pode ser aproximada para O(N).
 * 
 * - getInfo():
 *   - Retorna uma string fixa em O(1).
 * 
 * Portanto, a função principal receberDadosMesa é O(N), enquanto getInfo é O(1).
 */

@Path("/cassino")
public class Rotas {
    private static final Operacoes operacoes = new OperacoesImpl();
    private static final String CAMINHO_CHAVE_PRIVADA = "chave/chave_privada.key";
    private static final PrivateKey chavePrivada;

    static {
        PrivateKey tempChavePrivada = null;
        try {
            tempChavePrivada = Desencriptador.carregarChavePrivada(CAMINHO_CHAVE_PRIVADA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        chavePrivada = tempChavePrivada;
    }

    @POST
    @Path("/melhorGrupo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receberDadosMesa(String dadosCriptografados) {
        try {
            // Exibir chave privada usada na descriptografia
            String chavePrivadaBase64 = Base64.getEncoder().encodeToString(chavePrivada.getEncoded());
            System.out.println("[DEBUG] Chave privada usada para descriptografia (Base64): " + chavePrivadaBase64);

            // Exibir dados criptografados recebidos antes da descriptografia
            System.out.println("[DEBUG] Dados criptografados recebidos: " + dadosCriptografados);

            // Descriptografar os dados recebidos
            String jsonDescriptografado = Desencriptador.descriptografar(chavePrivada, dadosCriptografados);

            // Converter JSON para objeto MesaResultadoDTO
            MesaResultadoDTO resultado = new Gson().fromJson(jsonDescriptografado, MesaResultadoDTO.class);

            // Verifica se os dados da mesa são válidos
            if (resultado == null || resultado.getMelhoresJogadores().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Erro: Dados da mesa estão vazios ou mal formatados.")
                        .build();
            }

            // Exibir os dados recebidos após descriptografia
            System.out.println("===============================================");
            System.out.printf(" Dados recebidos da MESA %s\n", resultado.getMesaId());
            System.out.println("===============================================");
            System.out.printf(" Lucro total da mesa: %.2f\n", resultado.getSaldoFinalMesa());
            System.out.println(" Melhores jogadores:");
            System.out.println(" ID |    Nome Completo      | Saldo Inicial |  Saldo Final |   Lucro  ");
            for (Jogador jogador : resultado.getMelhoresJogadores()) {
                double lucroJogador = jogador.getSaldo() - jogador.getSaldoInicial();
                System.out.printf(" %2d | %-20s | %13.2f | %13.2f | %8.2f%n",
                        jogador.getId(), jogador.getNomeCompleto(),
                        jogador.getSaldoInicial(), jogador.getSaldo(), lucroJogador);
            }
            System.out.println("===============================================");

            operacoes.processarJogadores(resultado.getMelhoresJogadores());

            return Response.ok("Dados da mesa " + resultado.getMesaId() + " recebidos com sucesso!").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao processar dados criptografados: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/info")
    @Produces(MediaType.TEXT_PLAIN)
    public String getInfo() {
        return "API de gerenciamento de apostas do cassino, versão 1.0";
    }
}