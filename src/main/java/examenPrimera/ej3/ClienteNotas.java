package examenPrimera.ej3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClienteNotas {


    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        String dir = "localhost";
        int puerto = 6600;

        Socket socket = new Socket(dir, puerto);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        try {
            ArrayList<Asignatura> asignaturas = (ArrayList<Asignatura>) in.readObject();

            for (Asignatura asignatura : asignaturas) {
                boolean sigue = true;
                while (sigue) {

                    System.out.println("Introduce la nota de " + asignatura.nombre + ": ");
                    double nota = sc.nextDouble();
                    if (nota >= 0 && nota <= 10) {
                        asignatura.setNota(nota);
                        sigue = false;
                    }

                    System.out.println("Debes introducir una nota valida (0-10)");
                }
            }

            out.writeObject(asignaturas);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            double mediaPrimero = dataInputStream.readDouble();
            double mediaSegundo = dataInputStream.readDouble();
            double mediaTotal = dataInputStream.readDouble();
            System.out.println("Media de primero: " + mediaPrimero);
            System.out.println("Media de segundo: " + mediaSegundo);
            System.out.println("Media total: " + mediaTotal);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
