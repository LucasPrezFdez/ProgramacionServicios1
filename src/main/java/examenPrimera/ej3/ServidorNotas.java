package examenPrimera.ej3;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class ServidorNotas {


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        int puerto = 6600;

        ServerSocket servidor = new ServerSocket(puerto);

        Socket socket = servidor.accept();
        System.out.println("Cliente conectado.");
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        ArrayList<Asignatura> asignaturas = new ArrayList<Asignatura>();
        asignaturas.add(new Asignatura("Programación", 1));
        asignaturas.add(new Asignatura("Bases de Datos", 1));
        asignaturas.add(new Asignatura("Sistemas Operativos", 1));
        asignaturas.add(new Asignatura("Entornos", 1));
        asignaturas.add(new Asignatura("IPE", 1));
        asignaturas.add(new Asignatura("Ingles", 1));

        asignaturas.add(new Asignatura("Moviles", 2));
        asignaturas.add(new Asignatura("Redes", 2));
        asignaturas.add(new Asignatura("Intefaces", 2));
        asignaturas.add(new Asignatura("Sistemas y Gestion de Empresa", 2));
        asignaturas.add(new Asignatura("Habilidades", 2));
        asignaturas.add(new Asignatura("IPE II", 2));


        oos.writeObject(asignaturas);
        asignaturas = (ArrayList<Asignatura>) in.readObject();
        System.out.println("Notas recibidas calculando media...");

        double mediaPrimero = calcularMediaPrimero(asignaturas);
        double mediaSegundo = calcularMediaSegundo(asignaturas);
        double mediaTotal = (mediaPrimero + mediaSegundo) / 2;

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeDouble(mediaPrimero);
        dataOutputStream.writeDouble(mediaSegundo);
        dataOutputStream.writeDouble(mediaTotal);

    }

    public static double calcularMediaPrimero(ArrayList<Asignatura> asignaturas) {
        double suma = 0;
        int contador = 0;
        for (Asignatura asignatura : asignaturas) {
            if (asignatura.curso == 1) {
                suma += asignatura.nota;
                contador++;
            }
        }
        return suma / contador;
    }

    public static double calcularMediaSegundo(ArrayList<Asignatura> asignaturas) {
        double suma = 0;
        int contador = 0;
        for (Asignatura asignatura : asignaturas) {
            if (asignatura.curso == 2) {
                suma += asignatura.nota;
                contador++;
            }
        }
        return suma / contador;
    }
}
