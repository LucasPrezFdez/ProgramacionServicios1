package ser4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UppercaseServer {

    public static void main(String[] args) throws IOException {
        int puerto = 9876;

        DatagramSocket socket = new DatagramSocket(puerto);
        byte[] recibir = new byte[1024];
        byte[] enviar;

        System.out.println("Servidor UDP iniciado en puerto " + puerto);

        boolean sigue = true;
        while (sigue) {
            DatagramPacket recibirDatos = new DatagramPacket(recibir, recibir.length);
            socket.receive(recibirDatos);

            String frase = new String(recibirDatos.getData(), 0, recibirDatos.getLength());
            if (!frase.equalsIgnoreCase("exit")) {

                System.out.println("Recibido: " + frase);

                String respuesta = frase.toUpperCase();
                enviar = respuesta.getBytes();

                DatagramPacket enviarDatos = new DatagramPacket(
                        enviar,
                        enviar.length,
                        recibirDatos.getAddress(),
                        recibirDatos.getPort()
                );

                socket.send(enviarDatos);
            } else sigue = false;
        }

    }
}