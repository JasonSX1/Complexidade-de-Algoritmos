package br.edu.ifba.conversor;

public class App {

    //Complexidade constante, O(1)
    public static double toCelcius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public static double toFahrenheit(double celcius) {
        return celcius * 9 / 5 + 32;
    }

    public static void main(String[] args) throws Exception {

        double [] temperaturas = new double[]{54, 63, 70, 86};
        
        for(double temperatura: temperaturas){
            System.out.println("Fahrenheit: " + temperatura + " -> Celcius: " + toCelcius(temperatura));
        }
    }
}
