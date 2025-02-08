package br.edu.ifba.cassino.cliente.modelo;

import br.edu.ifba.cassino.cliente.sensoriamento.SensorDeApostas;
import br.edu.ifba.cassino.cliente.impl.ClienteImpl;
import java.util.ArrayList;
import java.util.List;

public class Mesa extends Thread {
    private final String mesaId;
    private final List<Jogador> jogadores;

    public Mesa(String mesaId, ClienteImpl cliente, int rodadasPorJogador) {
        this.mesaId = mesaId;
        this.jogadores = new ArrayList<>();
    }

    public String getMesaId() {
        return mesaId;
    }

    public synchronized void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
        System.out.println("[ " + mesaId + "] Jogador " + jogador.getNomeCompleto() + " adicionado. Total na mesa: "
                + jogadores.size());
    }

    @Override
    public void run() {

        while (!jogadores.isEmpty()) {
            processarApostas();
            renovarJogadores();
        }

        System.out.println("[ " + mesaId + "] Finalizou todas as rodadas de apostas.");
    }

    private void processarApostas() {
        System.out.println("[ " + mesaId + "] Processando apostas para " + jogadores.size() + " jogadores...");

        if (jogadores.isEmpty()) {
            System.err.println("[ERRO] Lista de jogadores está vazia na mesa " + mesaId);
            return;
        }

        SensorDeApostas.gerarApostasParaJogadores(jogadores, 5);
    }

    private void renovarJogadores() {
        System.out.println("[ " + mesaId + "] Renovando jogadores, removendo todos da mesa.");
        while (!jogadores.isEmpty()) {
            jogadores.remove(0);
        }
    }
}
