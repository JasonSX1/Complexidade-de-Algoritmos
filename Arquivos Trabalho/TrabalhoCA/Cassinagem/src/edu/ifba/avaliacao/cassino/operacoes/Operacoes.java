package edu.ifba.avaliacao.cassino.operacoes;
import java.util.List;
import java.util.Map;
import edu.ifba.avaliacao.cassino.impl.Jogador;
import edu.ifba.avaliacao.cassino.impl.Aposta;
import edu.ifba.avaliacao.cassino.impl.Roleta;

public interface Operacoes<Jogador, Aposta> {
    // Implementação d.1 - imprime as apostas realizadas
    void imprimir(List<Aposta> monitorados);

    // Implementação d.2 - imprime a lista de resultados da roleta e as apostas associadas a cada jogador
    SensorDeApostas.gerarApostasParaJogadores(jogadores, rodadas);

}
