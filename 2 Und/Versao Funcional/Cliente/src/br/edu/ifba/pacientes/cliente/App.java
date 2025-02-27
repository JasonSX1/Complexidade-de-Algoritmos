package br.edu.ifba.pacientes.cliente;

import java.util.List;

import com.github.javafaker.*;
import java.util.ArrayList;

import br.edu.ifba.pacientes.cliente.impl.ClienteImpl;
import br.edu.ifba.pacientes.cliente.impl.SensoriamentoImpl;
import br.edu.ifba.pacientes.cliente.modelo.Biometria;
import br.edu.ifba.pacientes.cliente.modelo.Paciente;

public class App {
    private static final int TOTAL_DE_PACIENTES = 100;

    public static void main(String[] args) throws Exception {
        List<Thread> processos = new ArrayList<>();

        for (int i = 0; i < TOTAL_DE_PACIENTES; i++) {
            String id = (i + 1) + "";
            String nome = "paciente#" + id;

            List<Biometria> padrao = new ArrayList<>();
            padrao.add(new Biometria(80, 0));
            padrao.add(new Biometria(85, 0));
            padrao.add(new Biometria(90, 0));


            ClienteImpl cliente = new ClienteImpl();
            cliente.configurar(new Paciente(id, nome), new SensoriamentoImpl(), padrao);

            Thread processo = new Thread(cliente);
            processos.add(processo);
            processo.start();
        }

        System.out.println("enviando biometrias para o servidor");

        for (Thread processo: processos) {
            processo.join();
        }

        System.out.println("biometrias enviadas, finalizando a execução");

    }
}
