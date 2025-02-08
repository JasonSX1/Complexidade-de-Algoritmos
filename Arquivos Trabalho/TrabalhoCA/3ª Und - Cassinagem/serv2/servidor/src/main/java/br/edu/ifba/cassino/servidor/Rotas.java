package br.edu.ifba.cassino.servidor;

import br.edu.ifba.cassino.servidor.impl.OperacoesImpl;
import br.edu.ifba.cassino.servidor.operacoes.Operacoes;
import br.edu.ifba.cassino.servidor.util.Desencriptador;
import br.edu.ifba.cassino.servidor.modelo.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.List;

import com.google.gson.Gson;



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
            // 🔑 Descriptografar os dados recebidos
            String jsonDescriptografado = Desencriptador.descriptografar(chavePrivada, dadosCriptografados);
    
            // 🔹 Converter JSON para objeto MesaResultadoDTO
            MesaResultadoDTO resultado = new Gson().fromJson(jsonDescriptografado, MesaResultadoDTO.class);
    
            // 📌 Verifica se os dados da mesa são válidos
            if (resultado == null || resultado.getMelhoresJogadores().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Erro: Dados da mesa estão vazios ou mal formatados.")
                        .build();
            }
    
            System.out.println("[SERVIDOR] Dados descriptografados recebidos.");
            operacoes.processarJogadores(resultado.getMelhoresJogadores());
    
            return Response.ok("Dados recebidos com sucesso!").build();
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