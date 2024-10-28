package br.edu.ifba.avaliacao.pacientes.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.edu.ifba.avaliacao.pacientes.operacoes.Operacoes;
import br.edu.ifba.avaliacao.pacientes.ordenador.Ordenador;

public class OperacoesImpl implements Operacoes<Paciente, Temperatura> {

    /**
     * metodo que imprime os pacientes
     * 
     * complexidade linear, O(N)
     */
    @Override
    public void imprimir(List<Paciente> monitorados) {
        for (Paciente paciente: monitorados) {
            System.out.println("paciente sendo monitorado: " + paciente);
        }
    }

    /**
     * complexidade quadrática, O(N²)
     */
    @Override
    public void imprimir(Map<Paciente, List<Temperatura>> leituras) {
        for (Paciente paciente: leituras.keySet()) {
            System.out.println("leituras do paciente " + paciente + ":");
            for (Temperatura leitura: leituras.get(paciente)) {
                System.out.println(leitura);
            }
        }
    }

    /**
     * complexidade O(N2^N)
     */
    @Override
    public Map<Paciente, List<Temperatura>> ordenar(Map<Paciente, List<Temperatura>> leituras) {
        Map<Paciente, List<Temperatura>> leiturasOrdenadas = new TreeMap<>();

        for (Paciente paciente: leituras.keySet()) {
            System.out.println("ordenando leituras do paciente: " + paciente);

            List<Temperatura> temperaturas = leituras.get(paciente);
            Ordenador<Temperatura> ordenador = new OrdenadorImpl(temperaturas);
            ordenador.ordenar();

            leiturasOrdenadas.put(paciente, temperaturas);
        }

        return leiturasOrdenadas;
    }

    /**
     * complexidade cubica, O(N^3)
     * 
     * tem a possibilidade de gerar uma situacao de brute force, porque exige
     * avaliar uma sequencia de dados biometricos de todos os pacientes monitorados. Dependendo da quantidade de pacientes e leituras realizadas, a execucao deste algoritmo pode elevar o seu tempo/custo e torna-lo ineficiente e ineficaz.
     */
    @Override
    public boolean procurarPadrao(Map<Paciente, List<Temperatura>> leituras, List<Temperatura> padrao) {
        int totalDeIguais = 0;

        inicioPesquisaPorPadrao:
        for (Paciente paciente: leituras.keySet()) {
            System.out.println("procurando por padrão nas leituras do paciente: " + paciente);

            List<Temperatura> temperaturas = leituras.get(paciente);
            for (int i = 0; i < temperaturas.size() - padrao.size(); i++) {
                for (int j = 0; j < padrao.size(); j++) {
                    if (temperaturas.get(i + j).getValor() == padrao.get(j).getValor()) {
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

        return (totalDeIguais == padrao.size());
    }
    
}
