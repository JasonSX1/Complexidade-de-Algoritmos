package br.edu.ifba.pacientes.cliente;

import java.util.List;
import java.util.Locale;
import java.util.ArrayList;

import com.github.javafaker.Faker;

import br.edu.ifba.pacientes.cliente.impl.ClienteImpl;
import br.edu.ifba.pacientes.cliente.impl.SensoriamentoImpl;
import br.edu.ifba.pacientes.cliente.modelo.Biometria;
import br.edu.ifba.pacientes.cliente.modelo.Paciente;

public class Cliente {
    private static final int TOTAL_DE_PACIENTES = 1;

    public static void main(String[] args) throws Exception {

        Faker geradorDedadosFalsos = new Faker(Locale.forLanguageTag("pt-BR"));

        List<Thread> processos = new ArrayList<>();

        for (int i = 0; i < TOTAL_DE_PACIENTES; i++) {
            // Gerando dados fictícios para o paciente
            String nome = geradorDedadosFalsos.name().fullName();
            System.out.println("Nome gerado: " + nome);
            String id = geradorDedadosFalsos.code().ean13();

            // Criando a lista de biometrias padrão para o paciente
            List<Biometria> padrao = new ArrayList<>();
            padrao.add(new Biometria(80, 0));
            padrao.add(new Biometria(85, 0));
            padrao.add(new Biometria(90, 0));

            // Configurando o cliente
            ClienteImpl cliente = new ClienteImpl();
            cliente.configurar(new Paciente(id, nome), new SensoriamentoImpl(), padrao);

            // Iniciando o processo em uma nova thread
            Thread processo = new Thread(cliente);
            processos.add(processo);
            processo.start();
        }

        System.out.println("Enviando biometrias para o servidor");

        // Esperando todas as threads finalizarem
        for (Thread processo : processos) {
            processo.join();
        }

        System.out.println("Biometrias enviadas, finalizando a execução");
    }
}
