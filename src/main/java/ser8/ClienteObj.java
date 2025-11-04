package ser8;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClienteObj {


    public static void main(String[] args) throws IOException {

        ArrayList<Cuakero> caravan = new ArrayList<>();

        // Instancias sencillas de 3 Cuakero
        Cuakero c1 = new Cuakero("Paco", 3, "blanco");
        Cuakero c2 = new Cuakero("Lola", 2, "gris");
        Cuakero c3 = new Cuakero("Toni", 4, "marrón");

        caravan.add(c1);
        caravan.add(c2);
        caravan.add(c3);

        // Mostrar información y que cuaken
        for (Cuakero c : caravan) {
            c.mostrarInfo();
            System.out.println();
        }

        String dir = "localhost";
        int port = 6500;

        Socket socket = new Socket(dir, port);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(caravan);

        System.out.println("Coleccion enviada...");

        oos.close();
        socket.close();
    }
}
