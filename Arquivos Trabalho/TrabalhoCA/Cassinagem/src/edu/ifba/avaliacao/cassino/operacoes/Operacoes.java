package edu.ifba.avaliacao.cassino.operacoes;
import java.util.List;
import java.util.Map;
import edu.ifba.avaliacao.cassino.impl.Jogador;
import edu.ifba.avaliacao.cassino.impl.Aposta;
import edu.ifba.avaliacao.cassino.impl.Roleta;

public interface Operacoes<Jogador, Aposta> {
    // Implementação d.1 - imprime os jogadores e seus saldos iniciais
    void imprimir(List<Aposta> monitorados);

    // Implementação d.2 - imprime a lista de resultados da roleta e as apostas associadas a cada jogador
    void apostar(Map<Jogador, List<Aposta>> leituras);

    // Implementação d.3 - ordena apostas por lucro
    Map<Jogador, List<Aposta>> ordenar(Map<Jogador, List<Aposta>> apostas);

    // Implementação d.4 - calcula retorno de uma aposta com base no resultado da roleta
    double calcularRetorno(Aposta aposta, Roleta resultado);
}
