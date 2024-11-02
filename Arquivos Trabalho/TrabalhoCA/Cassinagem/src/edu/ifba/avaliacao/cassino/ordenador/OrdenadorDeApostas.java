package edu.ifba.avaliacao.cassino.ordenador;

import java.util.List;
import edu.ifba.avaliacao.cassino.impl.Aposta;

public interface OrdenadorDeApostas {

    // Ordena as apostas pelo lucro em ordem crescente
    public List<Aposta> ordenarPorLucro(List<Aposta> apostas);
}