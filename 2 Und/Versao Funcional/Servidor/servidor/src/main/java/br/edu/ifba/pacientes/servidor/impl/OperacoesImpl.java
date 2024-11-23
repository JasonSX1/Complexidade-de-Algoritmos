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
     * complexidade cubica, O(N^3)
     * 
     * tem a possibilidade de gerar uma situacao de brute force, porque exige
     * avaliar uma sequencia de dados biometricos de todos os pacientes monitorados. Dependendo da quantidade de pacientes e leituras realizadas, a execucao deste algoritmo pode elevar o seu tempo/custo e torna-lo ineficiente e ineficaz.
     */
    @Override
    public int procurarPadrao(List<Biometria> padrao) {
        int totalDeIguais = 0;

        inicioPesquisaPorPadrao:
        for (Paciente paciente: bancoDeDados.keySet()) {
            System.out.println("procurando por padrão nas leituras do paciente: " + paciente);

            List<Biometria> biometrias = bancoDeDados.get(paciente);
            for (int i = 0; i < biometrias.size() - padrao.size(); i++) {
                for (int j = 0; j < padrao.size(); j++) {
                    if (biometrias.get(i + j).getBatimentos() == padrao.get(j).getBatimentos()) {
                        totalDeIguais++;

                        if (totalDeIguais == padrao.size()) {
                            break inicioPesquisaPorPadrao;
                        } 
                    } else {
                        totalDeIguais = 0;

                        break;
                    }
                }
            }
        }

        return totalDeIguais;
    }

    
}
