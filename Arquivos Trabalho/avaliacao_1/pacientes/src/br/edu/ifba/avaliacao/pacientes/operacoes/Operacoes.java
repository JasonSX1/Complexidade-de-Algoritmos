package br.edu.ifba.avaliacao.pacientes.operacoes;

import java.util.List;
import java.util.Map;

public interface Operacoes<Monitorado, Sensor> {

    // implementando d.1
    public void imprimir(List<Monitorado> monitorados);

    // implementando d.2
    public void imprimir(Map<Monitorado, List<Sensor>> leituras);

    // implementando d.3
    public Map<Monitorado, List<Sensor>> ordenar(Map<Monitorado, List<Sensor>> leituras);

    // implementando d.4
    public boolean procurarPadrao(Map<Monitorado, List<Sensor>> leituras, List<Sensor> padrao); 
    
}