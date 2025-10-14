package ser4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UppercaseClient {

    public static void main(String[] args) throws IOException {
        String servidorHost = "localhost";
        int puertoServidor = 9876;
        boolean sigue = true;
        Scanner sc = new Scanner(System.in);

        DatagramSocket client = new DatagramSocket();
        InetAddress adress = InetAddress.getByName(servidorHost);

        while (sigue) {
            System.out.println("Escribe algo: ");
            String frase = sc.nextLine();
            byte[] enviarBuffer = frase.getBytes();
            byte[] recibirBuffer = new byte[1024];
            DatagramPacket enviarPaquete = new DatagramPacket(enviarBuffer, enviarBuffer.length, adress, puertoServidor);
            client.send(enviarPaquete);
            if (!frase.equalsIgnoreCase("exit")) {

                DatagramPacket recibirPaquete = new DatagramPacket(recibirBuffer, recibirBuffer.length);
                client.receive(recibirPaquete);

                String respuesta = new String(recibirPaquete.getData(), 0, recibirPaquete.getLength());
                System.out.println("Respuesta del servidor: " + respuesta);
            } else sigue = false;

        }
    }
}