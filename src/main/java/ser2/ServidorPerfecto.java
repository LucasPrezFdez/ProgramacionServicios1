package ser2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorPerfecto {

    public static void main(String[] args) throws IOException {


        int puerto = 6000;
        Scanner sc = new Scanner(System.in);

        ServerSocket serverSocket = new ServerSocket(puerto);
        System.out.println("Iniciando servidor en el puerto: " + serverSocket.getLocalPort());

        Socket cliente = serverSocket.accept();
        System.out.println("Se ha conectado el cliente");

        DataInputStream dataInputStream = new DataInputStream(cliente.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(cliente.getOutputStream());

        boolean sigue = true;

        try {
            while (sigue) {
                String paquete = dataInputStream.readUTF();
                if (!paquete.equals("exit")) {
                    int numero = Integer.parseInt(paquete);
                    System.out.println("Numero recibido: " + paquete);

                    if (esPerfecto(numero)) {
                        System.out.println("Number is perfect");
                        dataOutputStream.writeUTF("El numero es perfecto");
                    } else {
                        System.out.println("Number inst perfect");
                        dataOutputStream.writeUTF("El numero no es perfecto");
                    }
                    System.out.println();
                    System.out.println("Operacion realizada, iniciando otro bucle");
                } else {
                    sigue = false;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    public static boolean esPerfecto(int numero) {
        if (numero <= 1) return false;
        int suma = 1;
        for (int i = 2; i <= numero / 2; i++) {
            if (numero % i == 0) {
                suma += i;
            }
        }
        return suma == numero;
    }
}
