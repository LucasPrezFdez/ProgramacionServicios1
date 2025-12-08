package examenA.ej3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerGemelo {

    public static void main(String[] args) throws IOException {

        int puerto = 6000;

        ServerSocket serverSocket = new ServerSocket(puerto);
        System.out.println("Server hosted en el puerto: " + puerto);

        Socket socket = serverSocket.accept();
        System.out.println("Se acaba de conectar un jitano");

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        boolean sigue = true;
        try {
            while (sigue) {

                Integer num1 = dataInputStream.readInt();
                Integer num2 = dataInputStream.readInt();

                if (num1 == 0 || num2 == 0) {
                    System.out.println("Cerrando conexion...");
                    sigue = false;

                } else {

                    if (num1 > 0 && num2 > 0) {
                        if (esGemelos(num1, num2)) {
                            dataOutputStream.writeUTF("Son numeros gemelos");
                        } else {
                            dataOutputStream.writeUTF("No son numeros gemelos");
                        }
                    } else {
                        dataOutputStream.writeUTF("Numeros no validos, han de ser positivos");
                    }

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static boolean esGemelos(int num1, int num2) {

        if (esPrimo(num1) && esPrimo(num2)) {
            if (Math.abs(num1 - num2) == 2) {
                return true;
            }
        }
        return false;
    }

    public static boolean esPrimo(int numero) {
        if (numero <= 1) return false;
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }

}
