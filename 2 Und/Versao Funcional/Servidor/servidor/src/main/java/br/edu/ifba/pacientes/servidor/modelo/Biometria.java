package br.edu.ifba.pacientes.servidor.modelo;

public class Biometria {
    
    private int batimentos;
    private int temperatura;
    

    public Biometria(int batimentos, int temperatura) {
        this.batimentos = batimentos;
        this.temperatura = temperatura;
    }

    public int getBatimentos() {
        return batimentos;
    }
    public void setBatimentos(int batimentos) {
        this.batimentos = batimentos;
    }

    public int getTemperatura() {
        return temperatura;
    }
    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    @Override
    public String toString() {

        return "temperatura: " + temperatura + ", batimentos: " + batimentos;
    }
}
