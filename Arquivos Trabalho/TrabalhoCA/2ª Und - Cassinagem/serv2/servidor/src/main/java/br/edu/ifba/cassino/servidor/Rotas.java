package br.edu.ifba.cassino.servidor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.gson.Gson;

import br.edu.ifba.cassino.servidor.impl.*;
import br.edu.ifba.cassino.servidor.modelo.*;
import br.edu.ifba.cassino.servidor.operacoes.*;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("cassino")
public class Rotas {

    private static final Operacoes operacoes = new OperacoesImpl();
    
    @POST
    @Path("jogador")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String gravarDadosJogador(JogadorDTO jogadorDTO) {
        System.out.println("Dados recebidos da mesa: " + jogadorDTO.getMesaId());
        System.out.println("Jogador: " + jogadorDTO.getNome());
        System.out.println("Saldo Inicial: " + jogadorDTO.getSaldoInicial());
        System.out.println("Saldo Final: " + jogadorDTO.getSaldoFinal());
        return "ok";
    }
    
    
    
    @POST
    @Path("grupo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String registrarMelhorGrupo(List<Jogador> grupo) {
        operacoes.registrarMelhorGrupo(grupo);
        return "Grupo registrado com sucesso.";
    }

    @POST
    @Path("melhorGrupo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String gravarMelhorGrupo(List<Jogador> melhorGrupo) {
        System.out.println("Melhor grupo recebido:");
        for (Jogador jogador : melhorGrupo) {
            System.out.println("Jogador: " + jogador.getNome() + ", Lucro: " + jogador.getLucro());
        }
        return "ok";
    }

    @GET
    @Path("jogadores")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Jogador> listarJogadores() {
        return operacoes.listarJogadores();
    }

    @GET
    @Path("info")
    @Produces(MediaType.TEXT_PLAIN)
    public String getInfo() {
        return "API de gerenciamento de apostas do cassino, versão 1.0";
    }

    @GET
    @Path("/")
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
