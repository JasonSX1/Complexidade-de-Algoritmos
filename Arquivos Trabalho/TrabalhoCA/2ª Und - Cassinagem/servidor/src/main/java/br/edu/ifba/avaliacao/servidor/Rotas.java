package br.edu.ifba.avaliacao.servidor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import br.edu.ifba.avaliacao.servidor.impl.*;
import br.edu.ifba.avaliacao.servidor.modelo.*;
import br.edu.ifba.avaliacao.servidor.operacoes.*;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("cassino")
public class Rotas {

    private static final Operacoes operacoes = new OperacoesImpl();

    @POST
    @Path("jogador")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String gravarDadosJogador(Jogador jogador) {
        operacoes.gravarDadosJogador(jogador);
        return "Jogador registrado: " + jogador.getNome();
    }

    @POST
    @Path("grupo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String registrarMelhorGrupo(List<Jogador> grupo) {
        operacoes.registrarMelhorGrupo(grupo);
        return "Grupo registrado com sucesso.";
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

}
