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
    @Path("{id}/{batimentos}/{temperatura}/{emergencia}")
    @Produces()
    public String gravarBiometria(@PathParam("id") String id, @PathParam("batimentos") int batimentos, @PathParam("temperatura")int temperatura, @PathParam("emergencia") int emergencias) {
        
        Biometria biometria = new Biometria(batimentos, temperatura);
        Paciente paciente = new Paciente(id, "paciente #" + id, emergencias > 0);
        getOperacoes().gravarBiometria(paciente, biometria);

        System.out.println("dados de biometria: " + biometria);
        
        return "ok";
    }

    @GET
    @Path("emergencias")
    @Produces(MediaType.TEXT_PLAIN)
    public String verificarEmergencia() {
        int emergencias = getOperacoes().detectarEmergencias().size();

        return emergencias + "";
    }

}
