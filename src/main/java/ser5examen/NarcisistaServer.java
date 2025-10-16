package ser5examen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class NarcisistaServer {



    public static void main(String[] args) throws IOException {
        int puerto = 11223;

        DatagramSocket socket = new DatagramSocket(puerto);
        byte[] recibir = new byte[1024];

        System.out.println("Servidor UDP iniciado en puerto " + puerto);

        boolean sigue = true;
        while (sigue) {

                DatagramPacket paqueteRecibir = new DatagramPacket(recibir, recibir.length);
                socket.receive(paqueteRecibir);

                String mensaje = new String(paqueteRecibir.getData(), 0, paqueteRecibir.getLength());


                System.out.println("Recibido de " + puerto + " numero: "+mensaje);

                String respuesta;

                if (mensaje.equalsIgnoreCase("exit")) {
                    System.out.println("Cerrando servidor...");
                    sigue = false;
                } else {
                    int numero = Integer.parseInt(mensaje);
                    boolean narcisista = esNarcisista(numero);
                    if (narcisista) {
                        respuesta = numero + " es NARCISISTA.";
                    } else {
                        respuesta = numero + " NO es narcisista.";
                    }
                    byte[] enviar = respuesta.getBytes();
                    DatagramPacket paqueteEnviar = new DatagramPacket(enviar, enviar.length, paqueteRecibir.getAddress(), paqueteRecibir.getPort());
                    socket.send(paqueteEnviar);
                }

        }

        System.out.println("Servidor detenido.");
    }



    private static boolean esNarcisista(int n) {
        int suma = 0;
        int valor = Math.abs(n);
        while (valor > 0) {
            int cifra = valor % 10;
            suma += cifra * cifra * cifra;
            valor /= 10;
        }
        return suma == n;
    }
}
