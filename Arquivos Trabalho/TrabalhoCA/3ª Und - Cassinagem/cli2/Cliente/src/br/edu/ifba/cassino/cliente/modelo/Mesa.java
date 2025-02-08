package br.edu.ifba.cassino.cliente.modelo;

import br.edu.ifba.cassino.cliente.sensoriamento.SensorDeApostas;
import br.edu.ifba.cassino.cliente.impl.ClienteImpl;
import java.util.LinkedList;
import java.util.List;

public class Mesa extends Thread {
    private final String mesaId;
    private final List<Jogador> jogadores;

    public Mesa(String mesaId, ClienteImpl cliente, int rodadasPorJogador) {
        this.mesaId = mesaId;
        this.jogadores = new LinkedList<>(); // O(1) - Inicialização da lista vazia (agora LinkedList)
    }

    public String getMesaId() {
        return mesaId; // O(1) - Retorno direto do atributo
    }

    public synchronized void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador); // O(1) - Inserção ao final da LinkedList
        System.out.println("[ " + mesaId + "] Jogador " + jogador.getNomeCompleto() + " adicionado. Total na mesa: "
                + jogadores.size()); // O(1) - Impressão na tela
    }

    @Override
    public void run() {
        while (!jogadores.isEmpty()) { // O(n) - Itera sobre os jogadores até que todos sejam removidos
            processarApostas(); // O(n) - Processa apostas para todos os jogadores
            renovarJogadores(); // O(n) - Remoção de todos os jogadores da lista
        }
        System.out.println("[ " + mesaId + "] Finalizou todas as rodadas de apostas."); // O(1) - Impressão
    }

    private void processarApostas() {
        System.out.println("[ " + mesaId + "] Processando apostas para " + jogadores.size() + " jogadores..."); // O(1)

        if (jogadores.isEmpty()) { // O(1) - Verificação se a lista está vazia
            System.err.println("[ERRO] Lista de jogadores está vazia na mesa " + mesaId);
            return;
        }

        SensorDeApostas.gerarApostasParaJogadores(jogadores, 5); // O(n) - Depende do número de jogadores
    }

    private void renovarJogadores() {
        System.out.println("[ " + mesaId + "] Renovando jogadores, removendo todos da mesa."); // O(1)

        jogadores.clear(); // O(1) - Agora a remoção de todos os elementos é eficiente com LinkedList
    }
}
