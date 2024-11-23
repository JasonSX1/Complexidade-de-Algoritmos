package br.edu.ifba.pacientes.cliente;

import java.util.List;
import java.util.Locale;

import com.github.javafaker.Faker;

import java.util.ArrayList;

import br.edu.ifba.pacientes.cliente.impl.ClienteImpl;
import br.edu.ifba.pacientes.cliente.impl.SensoriamentoImpl;
import br.edu.ifba.pacientes.cliente.modelo.Paciente;

public class Cliente {
    private static final int TOTAL_DE_PACIENTES = 100;

    public static void main(String[] args) throws Exception {

        Faker geradorDedadosFalsos = new Faker(Locale.forLanguageTag("pt-BR"));


        List<Thread> processos = new ArrayList<>();

        for (int i = 0; i < TOTAL_DE_PACIENTES; i++) {
            String nome = geradorDedadosFalsos.name().fullName();
            System.out.println("nome gerado: " + nome);
            String id = geradorDedadosFalsos.code().ean13();
            ClienteImpl cliente = new ClienteImpl();
            cliente.configurar(new Paciente(id, nome), new SensoriamentoImpl());

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
