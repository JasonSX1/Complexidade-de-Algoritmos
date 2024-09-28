package br.edu.ifba.arvore.impl;

import br.edu.ifba.arvore.estrutura.Arvore;
import br.edu.ifba.arvore.estrutura.Node;

public class ArvoreImpl extends Arvore<Integer> {

    private int passosDeBusca = 0;

    @Override
    public int getPassosDeBusca() {
        return passosDeBusca;
    }

    @Override
    public void imprimir() {
        StringBuilder visualizador = new StringBuilder();
        imprimir(visualizador, "", "", raiz);
        System.out.println(visualizador.toString());
    }

    private void imprimir(StringBuilder visualizador, String deslocamento, String apontador, Node<Integer> node) {
        if (node != null) {
            visualizador.append(deslocamento);
            visualizador.append(apontador);
            visualizador.append(node.getChave());
            visualizador.append("\n");

            StringBuilder visualizacaoApontador = new StringBuilder(deslocamento);
            visualizacaoApontador.append("│ ");

            String paraOsDoisLados = visualizacaoApontador.toString();
            String paraDireita = "└─D─";
            String paraEsquerda = (node.getDireita() != null) ? "├─E─" : "└─E─";

            imprimir(visualizador, paraOsDoisLados, paraEsquerda, node.getEsquerda());
            imprimir(visualizador, paraOsDoisLados, paraDireita, node.getDireita());
        }
    }

    @Override
    public Node<Integer> buscar(Node<Integer> raiz, Integer chave) {
        passosDeBusca++;
        if (raiz == null || raiz.getChave() == chave) {
            return raiz;
        }

        if (chave < raiz.getChave()){
            return buscar(raiz.getEsquerda(), chave);
        }

        return buscar(raiz.getDireita(), chave);
    }

    @Override
    public Node<Integer> inserir (Node<Integer> raiz, Integer chave) {
        if (raiz == null) {
            raiz = new Node<Integer>(chave);

            return raiz;
        }

        if (chave < raiz.getChave()) {
            raiz.setEsquerda(inserir(raiz.getEsquerda(), chave));
        } else if (chave > raiz.getChave()) {
            raiz.setDireita(inserir(raiz.getDireita(), chave));
        }
        return raiz;
    }
}
