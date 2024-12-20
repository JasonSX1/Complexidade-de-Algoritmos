import br.edu.ifba.avaliacao.cliente.impl.ClienteImpl;

public class App {
    public static void main(String[] args) {
        final int totalMesas = 10;
        final int jogadoresPorMesa = 5;

        for (int i = 0; i < totalMesas; i++) {
            ClienteImpl clienteMesa = new ClienteImpl();
            clienteMesa.configurar(jogadoresPorMesa);

            Thread threadMesa = new Thread(clienteMesa);
            threadMesa.setName("Mesa-" + (i + 1));
            threadMesa.start();

            System.out.println("Iniciando thread para " + threadMesa.getName());
        }
    }
}
