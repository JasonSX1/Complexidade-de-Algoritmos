package br.edu.ifba.avaliacao.pacientes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.edu.ifba.avaliacao.pacientes.impl.OperacoesImpl;
import br.edu.ifba.avaliacao.pacientes.impl.Paciente;
import br.edu.ifba.avaliacao.pacientes.impl.SensoriamentoImpl;
import br.edu.ifba.avaliacao.pacientes.impl.Temperatura;
import br.edu.ifba.avaliacao.pacientes.operacoes.Operacoes;
import br.edu.ifba.avaliacao.pacientes.sensoriamento.Sensoriamento;

public class App {

    private static final int TOTAL_PACIENTES = 10;
    private static final int TOTAL_LEITURAS = 10;

    public static void main(String[] args) throws Exception {
        Sensoriamento<Temperatura> sensoriamento = new SensoriamentoImpl();

        // gerando leituras para N pacientes
        Map<Paciente, List<Temperatura>> leituras = new TreeMap<>();
        for (int i = 0; i < TOTAL_PACIENTES; i++) {
            leituras.put(new Paciente(i + "", "luis #" + i), sensoriamento.gerar(TOTAL_LEITURAS));
        }

        Operacoes<Paciente, Temperatura> operacoes = new OperacoesImpl();

        // d.1 imprimindo os pacientes
        System.out.println("imprimindo os pacientes:");
        operacoes.imprimir(new ArrayList<Paciente>(leituras.keySet()));

        // d2. imprimindo leituras por paciente
        System.out.println("imprimindo leituras por cada paciente:");
        operacoes.imprimir(leituras);

        // d3. ordenando os dados das leituras por paciente
        System.out.println("ordenando as leituras de temperatura por cada paciente:");
        Map<Paciente, List<Temperatura>> leiturasOrdenadas = operacoes.ordenar(leituras);
        operacoes.imprimir(leiturasOrdenadas);

        // d4. procurando por um padrao de temperaturas nas leituras
        List<Temperatura> padrao = new ArrayList<>();
        padrao.add(new Temperatura(34));
        padrao.add(new Temperatura(36));

        System.out.println("procurando pelo padrão");
        boolean encontrado = operacoes.procurarPadrao(leituras, padrao);
        if (encontrado) {
            System.out.println("padrão encontrado");
        } else {
            System.out.println("padrão não encontrado");
        }
    }
}
