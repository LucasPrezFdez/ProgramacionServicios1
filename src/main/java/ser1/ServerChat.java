package ser1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerChat {

    public static void main(String[] args) throws IOException {

        int puerto = 6000; // Puerto
        // yo soy 10.101.21.152
        Scanner sc = new Scanner(System.in);

        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Escuchando en " + servidor.getLocalPort());

        // esperando a un cliente
        Socket cliente1 = servidor.accept();
        // realizar acciones con cliente1
        System.out.println("Se ha conectado el cliente.");
        try {
            while (true) {
                // VOY A ESCRIBIR ALGO
                DataInputStream in = new DataInputStream(servidor.getInputStream());
                System.out.println("Escribe algo chatin");
                String envio = in.readUTF();
                // Preparar respuesta

            }
        } catch (IOException e) {
            System.out.println("Finalizando el programa...");

            servidor.close(); // cierro socket servidor
        }
    }
}
