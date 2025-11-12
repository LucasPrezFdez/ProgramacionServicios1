package ser11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloOmirp extends Thread {

    private BufferedReader fentrada;
    private PrintWriter fsalida;
    private Socket socket;

    public HiloOmirp(Socket s) throws IOException {
        this.socket = s;
        // se crean flujos de entrada y salida
        this.fsalida = new PrintWriter(socket.getOutputStream(), true);
        this.fentrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String cadena;
            int num;
            boolean sigue = true;
            // lee líneas hasta recibir null o la cadena de terminación "***"
            while (sigue) {
                System.out.println("COMUNICO CON: " + socket.toString());
                cadena = fentrada.readLine();

                if (cadena.equals("quit"))
                    sigue = false;
                if (!parseCad(cadena))
                    fsalida.println("Debes introducir un numero!!");
                if (parseCad(cadena)) {
                    num = Integer.parseInt(cadena);
                    if (esOmirp(num)) {
                        fsalida.println("Si es primo");
                    } else {
                        fsalida.println("No es primo");
                    }
                }

            }
            System.out.println("FIN CON: " + socket.toString());
            fsalida.close();
            fentrada.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean esOmirp(int numero) {
        StringBuilder sb = new StringBuilder();
        StringBuilder reverseSb = new StringBuilder();
        sb.append(numero);
        reverseSb = sb.reverse();
        int n = Integer.parseInt(String.valueOf(reverseSb));

        if (n <= 1) return false;
        if (n <= 3) return true;       // 2 y 3 son primos
        if (n % 2 == 0) return false;  // descartar pares

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static boolean parseCad(String cadena) {
        int num;
        try {
            num = Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
