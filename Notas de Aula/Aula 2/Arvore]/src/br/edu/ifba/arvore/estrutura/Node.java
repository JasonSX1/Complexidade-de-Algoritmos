package br.edu.ifba.arvore.estrutura;

public class Node<Tipo> {

    private Tipo chave;
    private Node<Tipo> esquerda;
    private Node<Tipo> direita;

    public Node(Tipo chave) {
        this.chave = chave;
        this.esquerda = null;
        this.direita = null;
    }

    public Node<Tipo> getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(Node<Tipo> esquerda) {
        this.esquerda = esquerda;
    }

    public Node<Tipo> getDireita() {
        return direita;
    }

    public void setDireita(Node<Tipo> direita) {
        this.direita = direita;
    }

    public Tipo getChave(){
        return chave;
    }
}
