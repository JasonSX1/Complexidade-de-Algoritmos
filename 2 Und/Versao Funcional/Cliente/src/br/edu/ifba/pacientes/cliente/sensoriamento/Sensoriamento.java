package br.edu.ifba.pacientes.cliente.sensoriamento;

import java.util.List;

public interface Sensoriamento<Sensor> {

    public List<Sensor> gerar(int totalLeituras);

}
