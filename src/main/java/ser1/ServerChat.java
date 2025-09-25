package ser1;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChat {

    public static void main(String[] args) throws IOException {

        int puerto = 6000; // Puerto
        // yo soy 10.101.21.152

        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Escuchando en " + servidor.getLocalPort());

        // esperando a un cliente
        Socket cliente1 = servidor.accept();
        // realizar acciones con cliente1
        System.out.println("Se ha conectado el cliente.");
        DataInputStream in = new DataInputStream(cliente1.getInputStream());
        String msg = in.readUTF();
        System.out.println("Mensaje recibido: "+msg );

        // Preparar respuesta

        System.out.println("Finalizando el programa...");

        servidor.close(); // cierro socket servidor

    }
}
