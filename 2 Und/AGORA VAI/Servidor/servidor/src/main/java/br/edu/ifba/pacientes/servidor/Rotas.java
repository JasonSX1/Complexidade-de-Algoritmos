package br.edu.ifba.pacientes.servidor;

import br.edu.ifba.pacientes.servidor.modelo.Biometria;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("biometria")
public class Rotas {

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
}
