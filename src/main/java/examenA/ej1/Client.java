package examenA.ej1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 6000;


        try (Socket socket = new Socket(host, port);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.print("Nombre de usuario: ");
            String username = console.readLine();
            if (username == null || username.trim().isEmpty()) {
                System.out.println("Nombre inválido. Saliendo.");
                return;
            }
            out.println(username.trim());

            ThreadClient hilo = new ThreadClient(socket);
            hilo.start();

            String line;
            while ((line = console.readLine()) != null) {
                out.println(line);
                if ("/quit".equalsIgnoreCase(line.trim())) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
}

