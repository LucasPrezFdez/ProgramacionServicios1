package ser11;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorOmirp {

    public static void iniciarServidor() {
        int port = 6000;
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Servidor escuchando en puerto " + port);
            while (true) {
                Socket s = server.accept();
                HiloOmirp hilo = new HiloOmirp(s);
                hilo.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        iniciarServidor();
    }
}
