package edu.ifba.avaliacao.cassino.operacoes;

import java.util.List;
import java.util.Map;

import edu.ifba.avaliacao.cassino.impl.Aposta;

public interface OperacoesCassino<Jogador, Aposta> {

    // implementação d.1
    public void imprimir(List<Aposta> monitorados);

    // implementação d.2
    //public void imprimir(Map<Jogador, List<Entrada>> leituras);

    // implementação d.3
    //public Map<Monitorado, List<Sensor>> ordenar(Map<Monitorado, List<Sensor>> leituras);

    // implementação d.4
    //public boolean procurarPadrao(Map<Monitorado, List<Sensor>> leituras, List<Sensor> padrao); 
}