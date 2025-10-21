package ser5examen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class NarcisistaCliente {


    public static void main(String[] args) throws IOException {

        String servidor = "localhost";
        int puerto = 11223;

       DatagramSocket socket = new DatagramSocket(); Scanner sc = new Scanner(System.in);
            InetAddress direccion = InetAddress.getByName(servidor);
            System.out.println("Cliente UDP. Escriba un número entero de 3 cifras. (exit para salir)");

            boolean sigue = true;
            while (sigue) {
                System.out.print("Numero: ");
                String linea = sc.nextLine().trim();

                if (linea.equalsIgnoreCase("exit")) {
                    System.out.println("Desconectando cliente.");
                    sigue = false;
                }
                if (!esEntradaValida(linea)) {
                    System.out.println("Introduzca exactamente 3 dígitos. (exit para salir) ");
                } else {

                    // Enviar y recibir respuesta
                    byte[] enviar = linea.getBytes();
                    DatagramPacket paqueteEnviar = new DatagramPacket(enviar, enviar.length, direccion, puerto);
                    socket.send(paqueteEnviar);

                    byte[] paquete = new byte[1024];
                    DatagramPacket paqueteRecibir = new DatagramPacket(paquete, paquete.length);
                    socket.receive(paqueteRecibir);


                    String respuesta = new String(paqueteRecibir.getData(), 0, paqueteRecibir.getLength());
                    System.out.println("Servidor: " + respuesta);
                }

            }


    }

    private static boolean esEntradaValida(String msg) {
        if (msg == null) return false;
        if (msg.equalsIgnoreCase("exit")) return true;
        if (msg.length()!=3) return false;
        for (int i = 0; i<msg.length(); i++) {
            if (!Character.isDigit(msg.charAt(i))) return false;
        }
        return true;
    }
}
