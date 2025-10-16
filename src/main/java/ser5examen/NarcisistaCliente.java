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

                // Filtro local: permitir solo 3 dígitos o FIN
                if (!esEntradaValida(linea)) {
                    System.out.println("Introduzca exactamente 3 dígitos. (exit para salir) ");
                }

                // Enviar y recibir respuesta
                byte[] enviar = linea.getBytes();
                DatagramPacket paqueteEnviar = new DatagramPacket(enviar, enviar.length, direccion, puerto);
                socket.send(paqueteEnviar);

                byte[] buffer = new byte[1024];
                DatagramPacket paqueteRecibir = new DatagramPacket(buffer, buffer.length);
                socket.receive(paqueteRecibir);


                String respuesta = new String(paqueteRecibir.getData(), 0, paqueteRecibir.getLength());
                System.out.println("Servidor: " + respuesta);

                if (linea.equalsIgnoreCase("exit")) {
                    System.out.println("Desconectando cliente.");
                    sigue = false;
                }
            }


    }

    private static boolean esEntradaValida(String msg) {
        if (msg == null) return false;
        if (msg.equalsIgnoreCase("exit")) return true;
        return msg.length()==3;
    }
}
