package ser10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloServidor extends Thread {
    private BufferedReader fentrada;
    private PrintWriter fsalida;
    private Socket socket;

    public HiloServidor(Socket s) throws IOException {
        this.socket = s;
        // se crean flujos de entrada y salida
        this.fsalida = new PrintWriter(socket.getOutputStream(), true);
        this.fentrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String cadena = "";
            // lee líneas hasta recibir null o la cadena de terminación "***"
            while (!cadena.trim().equals("quit")) {
                System.out.println("COMUNICO CON: " + socket.toString());
                cadena = fentrada.readLine();
                System.out.println(cadena);
                if (cadena != null)
                    fsalida.println(cadena.trim().toUpperCase());
            }
            System.out.println("FIN CON: " + socket.toString());
            fsalida.close();
            fentrada.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


