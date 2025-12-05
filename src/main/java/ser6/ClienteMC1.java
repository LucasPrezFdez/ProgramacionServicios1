package ser6;

import java.io.*;
import java.net.*;

public class ClienteMC1 {
    public static void main(String args[]) throws Exception {

        // Se crea el socket multicast
        int puerto = 6789; // Puerto Multicast
        MulticastSocket ms = new MulticastSocket(puerto);

        InetAddress grupo = InetAddress.getByName("230.0.0.0"); // Grupo

        // Nos unimos al grupo
        ms.joinGroup(grupo);

        String msg = "";
        byte[] buf = new byte[1000];

        // Bucle de lectura hasta recibir "*"
        while (!msg.trim().equals("*")) {
            // Recibe el paquete del socket Multicast
            DatagramPacket paquete = new DatagramPacket(buf, buf.length);
            ms.receive(paquete);

            msg = new String(paquete.getData());
            System.out.println("Recibido: " + msg.trim());
        }

        ms.leaveGroup(grupo); // abandonamos grupo
        ms.close(); // cierra socket
        System.out.println("Socket cerrado...");
    }
}