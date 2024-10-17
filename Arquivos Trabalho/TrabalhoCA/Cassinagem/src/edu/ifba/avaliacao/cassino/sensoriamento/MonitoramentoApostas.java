package edu.ifba.avaliacao.cassino.sensoriamento;

import java.util.List;

public interface MonitoramentoApostas<TipoDado> {

    public List<TipoDado> gerar(int totalLeituras);
}