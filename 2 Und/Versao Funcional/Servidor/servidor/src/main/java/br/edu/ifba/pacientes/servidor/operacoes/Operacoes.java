package br.edu.ifba.pacientes.servidor.operacoes;

import java.util.List;

public interface Operacoes<Monitorado, Sensor> {

    public void gravarBiometria(Monitorado monitorado, Sensor sensor);

    public int procurarPadrao(List<Sensor> padrao); 

}

