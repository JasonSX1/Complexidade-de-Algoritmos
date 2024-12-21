package br.edu.ifba.cassino.cliente.comunicacao;

import br.edu.ifba.cassino.cliente.modelo.Jogador;

public interface Cliente {

    // Configura o cliente com os jogadores e o número de apostas
    void configurar(int quantidadeJogadores);

    // Executa o processamento das apostas e atualizações do melhor grupo
    void processar();

    // Envia os dados consolidados de um jogador ao servidor
    void enviarDadosJogador(Jogador jogador) throws Exception;

    // Envia o melhor grupo de jogadores ao servidor
    void enviarMelhorGrupo() throws Exception;
}
