package examenA.ej3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteGemelo {

    public static void main(String[] args) throws IOException {

        int puerto = 6000;
        String host = "localhost";

        Socket socket = new Socket(host, puerto);

        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;

        Scanner sc = new Scanner(System.in);

        boolean sigue = true;
        while (sigue) {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            String num1;
            String num2;

            System.out.println("Introduce el primer numero positivo (escribe 'exit' para salir): ");
            num1 = sc.nextLine();
            if (num1.equals("exit")) {
                dataOutputStream.writeInt(0);
                dataOutputStream.writeInt(0);
                System.out.println("Saliendo...");
                sigue = false;
                break;
            }
            System.out.println("Introduce el segundo numero positivo (escribe 'exit' para salir): ");
            num2 = sc.nextLine();

            if (num2.equals("exit")) {
                dataOutputStream.writeInt(0);
                dataOutputStream.writeInt(0);
                System.out.println("Saliendo...");
                sigue = false;
                break;
            }

            try {
                Integer primero = Integer.parseInt(num1);
                Integer segundo = Integer.parseInt(num2);

                if (primero != 0 || segundo != 0) {
                    dataOutputStream.writeInt(primero);
                    dataOutputStream.writeInt(segundo);
                    String respuesta = dataInputStream.readUTF();
                    System.out.println("Respuesta del servidor: " + respuesta);
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no valida, por favor introduce numeros enteros positivos.");
            }

        }


    }

}
