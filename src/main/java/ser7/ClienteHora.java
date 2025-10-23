package ser7;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class ClienteHora {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        int port = 12345;
        String host = "225.0.0.1";

        MulticastSocket socket = new MulticastSocket(port);
        InetAddress grupo = InetAddress.getByName(host);
        socket.joinGroup(grupo);

        String msg = "";
        boolean corte = true;

        System.out.println("Cliente conectado al multicast, recibiendo hora...");
        while (corte) {

            byte[] buf = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            // Recibir el paquete desde el socket
            msg = new String(dp.getData(), 0, dp.getLength());
            System.out.println("recibido: " + msg);
            System.out.println("seguir s/n");
            char resp = sc.nextLine().toLowerCase().trim().charAt(0);
            if (resp == 'n') {
                corte = false;
            }
        }
        System.out.println();
        socket.close();
        sc.close();
    }
}
