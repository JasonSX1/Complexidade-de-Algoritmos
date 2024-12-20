package br.edu.ifba.pacientes.servidor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.edu.ifba.pacientes.servidor.modelo.Biometria;
import br.edu.ifba.pacientes.servidor.modelo.Paciente;
import br.edu.ifba.pacientes.servidor.operacoes.Operacoes;

public class OperacoesImpl implements Operacoes<Paciente, Biometria> {

    private Map<Paciente, List<Biometria>> bancoDeDados = new TreeMap<>();


    //Complexidade linear, O(N)

    @Override
    public void gravarBiometria(Paciente monitorado, Biometria sensor) {
        List<Biometria> leituras = new ArrayList<>();        

        if (bancoDeDados.containsKey(monitorado)) {
            leituras = bancoDeDados.get(monitorado);
        } else {
            bancoDeDados.put(monitorado, leituras);
        }

        leituras.add(sensor);
    }

    /**
     * complexidade cubica, O(N)
     */
    @Override
    public List<Paciente> detectarEmergencias() {
        List<Paciente> pacientes = new ArrayList<>();

        for (Paciente paciente: bancoDeDados.keySet()) {
            if (paciente.emEmergencia()) {
                pacientes.add(paciente);
            }
        }

        return pacientes;
    }
        

    
}
