package ser1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat {
    public static void main(String[] args) {

        String host = "localhost"; // pepe ==> 10.101.3.86


        int puerto = 6000; // puerto remoto
        Scanner sc = new Scanner(System.in);
        // El server esta en ip 10.101.21.152
        // ABRIR SOCKET
        try {
            Socket cliente = new Socket(host, puerto); // conecta
//            InetAddress inet = cliente.getInetAddress();
//            System.out.println("IP local usada por el cliente: " +inet.toString());
//            System.out.println("Puerto local: " + cliente.getLocalPort());

            System.out.println("Datos de conexion externa");
            System.out.println("Puerto remoto: " + cliente.getPort());
            System.out.println("Host remoto: " + cliente.getInetAddress().getHostName());
            System.out.println("IP Host remoto: " + cliente.getInetAddress().toString());

            boolean sigue = true;
            while (sigue) {
                // VOY A ESCRIBIR ALGO
                DataOutputStream out = new DataOutputStream(cliente.getOutputStream());
                System.out.println("Escribe algo chatin (exit) para salir");
                String envio = sc.nextLine();
                out.writeUTF(envio);

                if (!envio.equals("exit")) {
                    //respuesta
                    DataInputStream in = new DataInputStream(cliente.getInputStream());
                    String msg = in.readUTF();
                    System.out.println("Mensaje recibido: " + msg);
                    System.out.println("=================================");
                } else {
                    System.out.println("Abandonando conexion...");
                    sigue = false;
                }
            }
            //cliente.close(); // cierra el socket
        } catch (IOException e) {
            System.err.println("Error de conexion: " + e.getMessage());
        }

    }
}
