package br.edu.ifba.avaliacao.pacientes.sensoriamento;

import java.util.List;

public interface Sensoriamento<TipoDado> {

    public List<TipoDado>   gerar(int totalLeituras);

}