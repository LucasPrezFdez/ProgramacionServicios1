package ser6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ServidorMC1 {
    public static void main(String[] args) throws Exception {
        // Flujo para entrada estándar
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        // Se crea el socket multicast
        MulticastSocket ms = new MulticastSocket(); // no se asocia a un puerto específico

        int puerto = 6789;  // Puerto multicast
        InetAddress grupo = InetAddress.getByName("230.0.0.0"); // Dirección del grupo

        String cadena = "";

        while(!cadena.equals("salir")){
            System.out.print("Datos a enviar al grupo: ");
            cadena = in.readLine();
            if (!cadena.equals("salir")){
                byte[] buf = cadena.getBytes();

                // ENVIANDO AL GRUPO
                DatagramPacket paquete = new DatagramPacket(
                        buf,
                        cadena.length(),
                        grupo,
                        puerto
                );
                ms.send(paquete);
            }
        }

        ms.close(); // Cierra el socket
        System.out.println("Socket cerrado...");
    }
}
