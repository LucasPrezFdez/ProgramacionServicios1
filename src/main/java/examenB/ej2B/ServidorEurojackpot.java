package examenB.ej2B;

import java.net.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.io.IOException;

public class ServidorEurojackpot {
    private static final int PUERTO = 9877;

    private static final Set<Integer> NUMEROS_GANADORES = new HashSet<>(Arrays.asList(1, 10, 25, 30, 45));
    private static final Set<Integer> SOLES_GANADORES = new HashSet<>(Arrays.asList(5, 8));

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(PUERTO);
        byte[] bufer = new byte[1024];

        System.out.println("Servicio UDP Eurojackpot iniciado en puerto " + PUERTO);

        while (true) {
            DatagramPacket paqueteRecibido = new DatagramPacket(bufer, bufer.length);
            socket.receive(paqueteRecibido);

            InetAddress IPCliente = paqueteRecibido.getAddress();
            int puertoCliente = paqueteRecibido.getPort();
            String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength()).trim();

            System.out.println("Recibido de " + IPCliente.getHostAddress() + ":" + puertoCliente + " -> " + mensaje);

            String respuesta = comprobarEurojackpot(mensaje);

            byte[] datosRespuesta = respuesta.getBytes();
            DatagramPacket paqueteEnviado = new DatagramPacket(datosRespuesta, datosRespuesta.length, IPCliente, puertoCliente);
            socket.send(paqueteEnviado);
        }
    }

    private static String comprobarEurojackpot(String combinacionStr) {
        try {
            String[] partes = combinacionStr.split("\\|");
            if (partes.length != 2) throw new IllegalArgumentException("Formato incorrecto.");

            String[] numsStr = partes[0].trim().split(" ");
            String[] solesStr = partes[1].trim().split(" ");

            if (numsStr.length != 5 || solesStr.length != 2) throw new IllegalArgumentException("Faltan números o soles.");

            Set<Integer> clienteNums = new HashSet<>();
            Set<Integer> clienteSoles = new HashSet<>();

            for (String s : numsStr) {
                int num = Integer.parseInt(s);
                if (num < 1 || num > 50) throw new IllegalArgumentException("Número fuera de rango.");
                clienteNums.add(num);
            }
            for (String s : solesStr) {
                int sol = Integer.parseInt(s);
                if (sol < 1 || sol > 12) throw new IllegalArgumentException("Sol fuera de rango.");
                clienteSoles.add(sol);
            }

            int aciertosNums = 0;
            for (int num : clienteNums) {
                if (NUMEROS_GANADORES.contains(num)) aciertosNums++;
            }

            int aciertosSoles = 0;
            for (int sol : clienteSoles) {
                if (SOLES_GANADORES.contains(sol)) aciertosSoles++;
            }

            return String.format("Aciertos: %d números y %d soles.", aciertosNums, aciertosSoles);

        } catch (Exception e) {
            return "ERROR en el formato de datos: " + e.getMessage();
        }
    }
}