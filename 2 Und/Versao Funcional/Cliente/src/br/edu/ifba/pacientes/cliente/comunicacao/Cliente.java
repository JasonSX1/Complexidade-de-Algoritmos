package br.edu.ifba.pacientes.cliente.comunicacao;

import java.util.List;

import br.edu.ifba.pacientes.cliente.sensoriamento.Sensoriamento;

public interface Cliente<Monitorado, Sensor> {

    public void configurar(Monitorado monitorado, Sensoriamento<Sensor> sensoriamento, List<Sensor> padrao);

    public int detectarEmergencias();

    public String enviar(Sensor sensor) throws Exception;

}
