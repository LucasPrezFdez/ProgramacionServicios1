package examenPrimera.ej2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloGemelo extends Thread {

    private BufferedReader fentrada;
    private PrintWriter fsalida;
    private Socket socket;

    public HiloGemelo(Socket s) throws IOException {
        this.socket = s;
        this.fsalida = new PrintWriter(socket.getOutputStream(), true);
        this.fentrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {

        try {
            String entrada1 = "";
            String entrada2 = "";
            int num1;
            int num2;

            while (true) {
                System.out.println("COMUNICO CON: " + socket.toString());
                entrada1 = fentrada.readLine();
                entrada2 = fentrada.readLine();

                try {
                    num1 = Integer.parseInt(entrada1);
                    num2 = Integer.parseInt(entrada2);

                    if (esGemelos(num1, num2))
                        fsalida.println("Son dos numeros Gemelos");
                    else
                        fsalida.println("No son dos Numeros Gemelos");


                } catch (NumberFormatException e) {
                    fsalida.println("Introduce un numero valido");
                }

                String sigue = fentrada.readLine();
                if (sigue.equals("quit"))
                    break;

            }
            System.out.println("FIN CON: " + socket.toString());
            fsalida.close();
            fentrada.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
