package ser8;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerObj {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ArrayList<Cuakero> entrada = new ArrayList<>();
        int puerto = 6500;
        ServerSocket server = new ServerSocket(puerto);

        Socket socket = server.accept();

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        entrada = (ArrayList<Cuakero>) ois.readObject();

        for (Cuakero pajaro : entrada){
            pajaro.mostrarInfo();
        }

        ois.close();
        socket.close();
        server.close();

        System.out.println("Cerrando el server...");

    }
}
