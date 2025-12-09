package examenPrimera.ej2;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerGemelo {

    public static void iniciarServidor() {
        int port = 6000;
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Servidor escuchando en puerto " + port);
            while (true) {
                Socket s = server.accept();
                HiloGemelo hilo = new HiloGemelo(s);
                hilo.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        iniciarServidor();
    }

    public static boolean esGemelos(int num1, int num2) {

        if (esPrimo(num1) && esPrimo(num2)) {
            if (Math.abs(num1 - num2) == 2) {
                return true;
            }
        }
        return false;
    }

    public static boolean esPrimo(int numero) {
        if (numero <= 1) return false;
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }

}
