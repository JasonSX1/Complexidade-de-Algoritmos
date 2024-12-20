package edu.ifba.avaliacao.cassino.operacoes;

public interface Operacoes {

    // d.1 - Imprime as informações sobre os jogadores e seus saldos iniciais
    void gerarJogadores(int quantidade);

    // d.2 - Imprime as apostas e os resultados da roleta associados a cada jogador
    void gerarApostas(int rodadas);

    // d.2.1 - Imprime o resultado final do saldo dos jogadores
    void resultadoAposApostas();

    // d.3 - Ordena as apostas por lucro e imprime
    void ordenarApostas();

    // d.4 - Calcula os melhores resultados para grupos de 3 jogadores e retorna o
    // grupo com maior lucro
    void calcularMelhoresResultadosGruposDeTres();

}
