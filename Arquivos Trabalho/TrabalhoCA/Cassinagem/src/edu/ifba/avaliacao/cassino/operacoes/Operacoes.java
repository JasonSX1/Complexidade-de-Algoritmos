package edu.ifba.avaliacao.cassino.operacoes;

import java.util.List;
import java.util.Map;
import edu.ifba.avaliacao.cassino.impl.Jogador;
import edu.ifba.avaliacao.cassino.impl.Aposta;
import edu.ifba.avaliacao.cassino.impl.Roleta;

public interface Operacoes<Jogador, Aposta> {

    // d.1 - Imprime as informações sobre os jogadores e seus saldos iniciais
    void gerarJogadores(int quantidade);

    // d.2 - Imprime as apostas e os resultados da roleta associados a cada jogador
    void gerarApostas(int rodadas);

    // d.2.1 - Imprime o resultado final do saldo dos jogadores
    void resultadoAposApostas();

    // d.3 - Ordena as apostas por lucro e imprime
    Map<Jogador, List<Aposta>> ordenarApostas(Map<Jogador, List<Aposta>> apostas);

    // d.4 - Calcula o retorno de uma aposta com base no resultado da roleta
    //double calcularRetorno(Aposta aposta, Roleta resultado);
}
