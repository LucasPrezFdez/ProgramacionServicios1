
package examenPrimera.ej1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) throws IOException {
        String servidorHost = "localhost";
        int puertoServidor = 9876;
        boolean sigue = true;
        Scanner sc = new Scanner(System.in);

        DatagramSocket client = new DatagramSocket();
        InetAddress adress = InetAddress.getByName(servidorHost);

        ArrayList<Integer> numerosIngresados = new ArrayList<>();

        while (sigue) {
            System.out.println("Escribe el numero a verificar (01-49) o '0' para salir: ");
            String frase = sc.nextLine().trim();

            byte[] enviarBuffer;
            byte[] recibirBuffer = new byte[1024];

            if (!"0".equalsIgnoreCase(frase)) {
                // Recolectar hasta 3 números válidos (dos dígitos cada uno)
                while (numerosIngresados.size() < 3) {
                    try {
                        int numero = Integer.parseInt(frase);
                        if (numero < 1 || numero > 49) {
                            System.out.println("Número fuera de rango. Introduce un número entre 01 y 49:");
                        } else {
                            numerosIngresados.add(numero);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Introduce un número válido entre 01 y 49:");
                    }
                    if (numerosIngresados.size() < 3) {
                        System.out.println("Escribe el numero a verificar (01-49): ");
                        frase = sc.nextLine().trim();
                    }
                }

                // Construir la cadena a enviar a partir de los 3 números (ej: "010203")
                StringBuilder sb = new StringBuilder();
                for (int n : numerosIngresados) {
                    sb.append(String.format("%02d", n));
                }
                String fraseEnviar = sb.toString();

                enviarBuffer = fraseEnviar.getBytes();
                DatagramPacket enviarPaquete = new DatagramPacket(enviarBuffer, enviarBuffer.length, adress, puertoServidor);
                client.send(enviarPaquete);

                DatagramPacket recibirPaquete = new DatagramPacket(recibirBuffer, recibirBuffer.length);
                client.receive(recibirPaquete);

                String respuesta = new String(recibirPaquete.getData(), 0, recibirPaquete.getLength());
                System.out.println("Respuesta del servidor: " + respuesta);

                // Reiniciar para la próxima ronda
                numerosIngresados.clear();

            } else {
                // Enviar exactamente "0" para indicar cierre
                enviarBuffer = "0".getBytes();
                DatagramPacket enviarPaquete = new DatagramPacket(enviarBuffer, enviarBuffer.length, adress, puertoServidor);
                client.send(enviarPaquete);

                DatagramPacket recibirPaquete = new DatagramPacket(recibirBuffer, recibirBuffer.length);
                client.receive(recibirPaquete);

                String respuesta = new String(recibirPaquete.getData(), 0, recibirPaquete.getLength());
                System.out.println("Respuesta del servidor: " + respuesta);
                sigue = false;
            }
        }

        client.close();
        sc.close();
    }
}
