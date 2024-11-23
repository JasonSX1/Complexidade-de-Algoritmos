package br.edu.ifba.pacientes.servidor;

import br.edu.ifba.pacientes.servidor.modelo.Biometria;

import br.edu.ifba.pacientes.servidor.modelo.Paciente;
import br.edu.ifba.pacientes.servidor.operacoes.*;
import br.edu.ifba.pacientes.servidor.modelo.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import br.edu.ifba.pacientes.servidor.impl.*;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("biometria")
public class Rotas {

    // singleton => padrão de instancia unica
    private static Operacoes<Paciente, Biometria> operacoes = null;
    public static Operacoes<Paciente, Biometria> getOperacoes() {
        if (operacoes == null) {
            operacoes = new OperacoesImpl();
        }

        return operacoes;
    }


    @GET
    @Path("informacoes")
    @Produces(MediaType.TEXT_PLAIN)
    public String getInformacoes() {
        return "serviço que processa biometria de pacientes, versão 1.0";
    }


    @GET 
    @Path("{id}/{batimentos}/{temperatura}")
    @Produces()
    public String gravarBiometria(@PathParam("id") String id, @PathParam("batimentos") int batimentos, @PathParam("temperatura")int temperatura){
        
        Biometria biometria = new Biometria(batimentos, temperatura);
        System.out.println("dados de biometria: " + biometria);
        return "ok";
    }

    @GET
    @Path("{id}/{nome}/{batimentos}/{temperatura}")
    @Produces(MediaType.TEXT_PLAIN)
    public String gravarBiometria(@PathParam("id") String id,@PathParam("nome") String nome, @PathParam("batimentos") int batimentos, @PathParam("temperatura") int temperatura) {
        Biometria biometria = new Biometria(batimentos, temperatura);
        Paciente paciente;
        try{
            paciente = new Paciente(id, URLDecoder.decode(nome, StandardCharsets.UTF_8.toString()));
            getOperacoes().gravarBiometria(paciente, biometria);

            System.out.println("recebidos dados de biometria (" + biometria + ") do paciente: " + paciente);
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        
        return "ok";
    }

}
