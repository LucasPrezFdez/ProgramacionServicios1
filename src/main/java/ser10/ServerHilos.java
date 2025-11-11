package ser10;

import java.io.*;
import java.net.*;

public class ServerHilos {

    public static void iniciarServidor() {
        int port = 6000;
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Servidor escuchando en puerto " + port);
            while (true) {
                Socket s = server.accept();
                HiloServidor hilo = new HiloServidor(s);
                hilo.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        iniciarServidor();
    }

    // Clase interna que maneja la comunicación con un cliente

}
