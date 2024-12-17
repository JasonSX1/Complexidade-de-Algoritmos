package br.edu.ifba.encriptacao.impl;

import java.security.KeyPair;
import java.util.Base64;

import javax.crypto.Cipher;

import br.edu.ifba.encriptacao.excecoes.FalhaEncriptacao;
import br.edu.ifba.encriptacao.encriptador.Encriptador;

public class EncriptadorImpl extends Encriptador {
    
    public EncriptadorImpl(KeyPair chaves, String algoritmoDeEncriptacao){
        super(chaves, algoritmoDeEncriptacao);
    }

    @Override
    public String encriptar(String dados) throws FalhaEncriptacao {
        String encriptacao = "";

        try {
            Cipher cifrador = Cipher.getInstance(algoritmoDeEncriptacao);
            cifrador.init(Cipher.ENCRYPT_MODE, chaves.getPublic());

            byte[] cifragem = cifrador.doFinal(dados.getBytes());

            encriptacao = Base64.getEncoder().encodeToString(cifragem);
        } catch (Exception e) {
            throw new FalhaEncriptacao("erro na encriptação"); 
        }

        return encriptacao;
    }

    @Override
    public String decriptar(String encriptacao) throws FalhaEncriptacao {
        String dados = "";

        try {
            Cipher cifrador = Cipher.getInstance(algoritmoDeEncriptacao);
            cifrador.init(Cipher.ENCRYPT_MODE, chaves.getPrivate());

            byte[] bytes = Base64.getDecoder().decode(encriptacao);
            byte[] bytesDecriptados = cifrador.doFinal(bytes);

            dados = new String(bytesDecriptados);

        } catch (Exception e) {
            throw new FalhaEncriptacao("erro na encriptação"); 
        }

        return dados;
    }
}