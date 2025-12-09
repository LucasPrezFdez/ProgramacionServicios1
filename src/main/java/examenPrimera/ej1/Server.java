package examenPrimera.ej1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Server {

    final static String GANADOR = "224944";

    public static void main(String[] args) throws IOException {
        int puerto = 9876;
        DatagramSocket socket = new DatagramSocket(puerto);

        System.out.println("Servidor UDP iniciado en puerto " + puerto);

        boolean sigue = true;
        while (sigue) {
            byte[] recibir = new byte[1024];

            DatagramPacket recibirDatos = new DatagramPacket(recibir, recibir.length);
            socket.receive(recibirDatos);

            String frase = new String(recibirDatos.getData(), 0, recibirDatos.getLength()).trim();
            System.out.println("Recibido: " + frase);

            if ("0".equalsIgnoreCase(frase)) {
                System.out.println("Se recibió '0' — cerrando servidor.");
                String cad = "Cerrando servidor";
                DatagramPacket enviarDatos = new DatagramPacket(
                        cad.getBytes(),
                        cad.length(),
                        recibirDatos.getAddress(),
                        recibirDatos.getPort()
                );
                socket.send(enviarDatos);
                sigue = false;
                break;
            }

            byte[] respuesta = ganador(frase);

            DatagramPacket enviarDatos = new DatagramPacket(
                    respuesta,
                    respuesta.length,
                    recibirDatos.getAddress(),
                    recibirDatos.getPort()
            );

            socket.send(enviarDatos);
        }

        socket.close();
    }

    private static byte[] ganador(String frase) {
        byte[] enviar;
        int aciertos = 0;
        try {
            char[] numeros = frase.toCharArray();

            for (int i = 0; i < GANADOR.length(); i++) {
                if (numeros[i] == GANADOR.charAt(i)) {
                    aciertos++;
                }
            }

            if (aciertos > 2) {
                String respuesta = "Has tenido " + aciertos;
                enviar = respuesta.getBytes();
                return enviar;
            } else {
                enviar = "Lo siento, no has sido premiado.".getBytes();
            }


        } catch (NumberFormatException e) {
            enviar = "No has introducido un número válido.".getBytes();
        }
        return enviar;
    }
}
