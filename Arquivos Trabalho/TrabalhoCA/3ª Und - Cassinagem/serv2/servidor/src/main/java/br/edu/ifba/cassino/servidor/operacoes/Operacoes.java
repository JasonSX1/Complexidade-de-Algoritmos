package br.edu.ifba.cassino.servidor.operacoes;

import br.edu.ifba.cassino.servidor.modelo.Jogador;
import java.util.List;

public interface Operacoes {
    // Processa os jogadores recebidos do cliente e os registra no servidor
    void processarJogadores(List<Jogador> jogadores);

    // Retorna os 3 melhores jogadores com base no saldo final
    List<Jogador> getMelhoresJogadores();
}
