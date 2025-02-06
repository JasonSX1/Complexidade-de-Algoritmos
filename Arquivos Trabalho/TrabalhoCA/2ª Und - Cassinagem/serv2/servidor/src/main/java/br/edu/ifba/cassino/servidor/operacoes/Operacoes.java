// Interface que define as operações do Servidor
package br.edu.ifba.cassino.servidor.operacoes;

import br.edu.ifba.cassino.servidor.modelo.Jogador;
import java.util.List;

public interface Operacoes {
    // Processa os jogadores recebidos do cliente
    void processarJogadores(List<Jogador> jogadores);

    // Retorna os 3 jogadores mais lucrativos
    List<Jogador> getMelhoresJogadores();
}
