package br.edu.ifba.arvore.estrutura;

public abstract class Arvore<Tipo> {
    protected Node<Tipo> raiz = null;
    

    public void inserir(Tipo chave){
        raiz = inserir(raiz, chave);
    }

    public Node<Tipo> buscar(Tipo chave) {
        return buscar(raiz, chave);
    }

    public abstract void imprimir();

    public abstract Node<Tipo> buscar(Node<Tipo> raiz, Tipo chave);

    public abstract Node<Tipo> inserir (Node<Tipo> raiz, Tipo chave);

    public abstract int getPassosDeBusca();
}
