package br.edu.ifba.busca.buscadores;

public class ResultadoBusca {

    private boolean encontrado = false;
    private int passosExecutados = 0;

    public boolean isEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

    public int getPassosExecutados() {
        return passosExecutados;
    }

    public void setPassosExecutados(int passosExecutados) {
        this.passosExecutados = passosExecutados;
    }
}
