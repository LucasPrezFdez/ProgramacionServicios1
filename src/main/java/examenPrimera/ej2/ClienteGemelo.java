package examenPrimera.ej2;

import java.io.*;
import java.net.Socket;

public class ClienteGemelo {

    public static void main(String[] args) {

        String host = "localhost";
        int puerto = 6000;


        try (
                Socket socket = new Socket(host, puerto);
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        ) {
            String mensaje;
            System.out.println("Conectado al servidor. Escribe mensajes para enviar ( quit para salir ):");

            while (true) {
                System.out.println("Numeros Gemelos: ");
                System.out.println("Numero 1: ");
                mensaje = teclado.readLine();
                salida.println(mensaje);
                System.out.println("Numero 2: ");
                mensaje = teclado.readLine();
                salida.println(mensaje);


                String respuesta = entrada.readLine();
                System.out.println("Respuesta del servidor: " + respuesta);

                System.out.println("Seguimos con esto? (Para salir escriba 'quit')");
                mensaje= teclado.readLine();
                if (mensaje.trim().equals("quit")) {
                    salida.println(mensaje);
                    System.out.println("Fin de transmisión.");
                    break;
                } else {
                    System.out.println("Nuevo bucle");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
