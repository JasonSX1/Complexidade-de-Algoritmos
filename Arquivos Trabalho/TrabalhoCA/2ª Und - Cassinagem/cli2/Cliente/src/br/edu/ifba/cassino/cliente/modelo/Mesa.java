package br.edu.ifba.cassino.cliente.modelo;

import br.edu.ifba.cassino.cliente.sensoriamento.SensorDeApostas;
import br.edu.ifba.cassino.cliente.impl.ClienteImpl;
import java.util.ArrayList;
import java.util.List;

public class Mesa extends Thread {
    private final String mesaId;
    private final ClienteImpl cliente;
    private final List<Jogador> jogadores;
    private double saldoFinal;
    private int rodadasPorJogador;

    public Mesa(String mesaId, ClienteImpl cliente, int rodadasPorJogador) {
        this.mesaId = mesaId;
        this.cliente = cliente;
        this.jogadores = new ArrayList<>();
        this.saldoFinal = 0.0;
        this.rodadasPorJogador = rodadasPorJogador;
    }

    public String getMesaId() {
        return mesaId;
    }

    public synchronized void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
        System.out.println("[MESA " + mesaId + "] Jogador " + jogador.getNomeCompleto() + " adicionado. Total na mesa: "
                + jogadores.size());
    }

    @Override
    public void run() {
        System.out.println("[DEBUG] Iniciando thread da mesa " + mesaId);

        while (!jogadores.isEmpty()) {
            System.out.println("[DEBUG] Chamando processarApostas() para a mesa " + mesaId);
            processarApostas();
            renovarJogadores();
        }

        System.out.println("[MESA " + mesaId + "] Finalizou todas as rodadas de apostas.");
    }

    private void processarApostas() {
        System.out.println("[MESA " + mesaId + "] Processando apostas para " + jogadores.size() + " jogadores...");

        if (jogadores.isEmpty()) {
            System.err.println("[ERRO] Lista de jogadores está vazia na mesa " + mesaId);
            return;
        }

        for (Jogador jogador : jogadores) {
            System.out.println("[DEBUG] Jogador na mesa: " + jogador.getNomeCompleto());
        }

        // 🔹 GARANTINDO QUE AS APOSTAS SERÃO GERADAS
        System.out.println("[DEBUG] Chamando gerarApostasParaJogadores() antes de apostar.");
        SensorDeApostas.gerarApostasParaJogadores(jogadores, 5);

        for (Jogador jogador : jogadores) {
            System.out.println("[DEBUG] Verificando histórico de apostas antes de apostar: "
                    + jogador.getHistoricoApostas().size());
            jogador.apostar();
        }
    }

    private void enviarResultados() {
        List<Jogador> melhoresJogadores = obterMelhoresJogadores();
        saldoFinal = calcularSaldoFinal();

        MesaResultadoDTO resultadoDTO = new MesaResultadoDTO(mesaId, saldoFinal, melhoresJogadores);
        cliente.enviarDadosMesa(); // Alterado para chamar o método correto!
    }

    private List<Jogador> obterMelhoresJogadores() {
        List<Jogador> melhores = new ArrayList<>();
        double maiorLucro = Double.NEGATIVE_INFINITY;
        int jogadoresConsiderados = 0;

        for (Jogador jogador : jogadores) {
            double lucro = jogador.getSaldo() - jogador.getSaldoInicial();
            if (jogadoresConsiderados < 3) {
                if (melhores.isEmpty() || lucro > maiorLucro) {
                    melhores.add(jogador);
                    maiorLucro = lucro;
                }
                jogadoresConsiderados++;
            }
        }
        return melhores;
    }

    private double calcularSaldoFinal() {
        double total = 0.0;
        for (Jogador jogador : jogadores) {
            total += jogador.getSaldo();
        }
        return total;
    }

    private void renovarJogadores() {
        System.out.println("[MESA " + mesaId + "] Renovando jogadores, removendo todos da mesa.");
        while (!jogadores.isEmpty()) {
            jogadores.remove(0);
        }
    }
}
