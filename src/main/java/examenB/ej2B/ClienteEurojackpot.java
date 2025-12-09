package examenB.ej2B;

import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;

public class ClienteEurojackpot {
    private static final String HOST = "localhost";
    private static final int PUERTO = 9877;

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress IP_SERVIDOR = InetAddress.getByName(HOST);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Cliente Eurojackpot iniciado.");

        try {
            boolean repetir = true;
            while (repetir) {
                String combinacion = pedirYFiltrarCombinacion(scanner);

                if (combinacion == null) {
                    repetir = false;
                    System.out.println("Finalizando aplicación...");
                    continue;
                }

                    byte[] datosEnvio = combinacion.getBytes();
                DatagramPacket paqueteEnvio = new DatagramPacket(datosEnvio, datosEnvio.length, IP_SERVIDOR, PUERTO);
                socket.send(paqueteEnvio);

                byte[] buferRespuesta = new byte[1024];
                DatagramPacket paqueteRespuesta = new DatagramPacket(buferRespuesta, buferRespuesta.length);
                socket.receive(paqueteRespuesta);

                String respuestaServidor = new String(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());
                System.out.println("Respuesta del Servidor: " + respuestaServidor);
                System.out.println("------------------------------------------");

                System.out.print("¿Desea introducir otra combinación? (S/N): ");
                String respuestaRepetir = scanner.next().trim();
                if (!respuestaRepetir.equalsIgnoreCase("S")) {
                    repetir = false;
                }
                scanner.nextLine();
            }
        } finally {
            socket.close();
            scanner.close();
        }
    }

    private static String pedirYFiltrarCombinacion(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Introduce CINCO números (1-50):");
                String numsStr = scanner.nextLine().trim();
                String[] numsArray = numsStr.split("\\s+");
                if (numsArray.length != 5) {
                    System.err.println("ERROR: Se requieren exactamente 5 números.");
                    continue;
                }
                Set<Integer> nums = new HashSet<>();
                for (String s : numsArray) {
                    int num = Integer.parseInt(s);
                    if (num < 1 || num > 50 || !nums.add(num)) {
                        System.err.println("ERROR: Números fuera de rango (1-50) o repetidos.");
                        throw new InputMismatchException();
                    }
                }

                System.out.println("Introduce DOS Soles (1-12):");
                String solesStr = scanner.nextLine().trim();
                String[] solesArray = solesStr.split("\\s+");
                if (solesArray.length != 2) {
                    System.err.println("ERROR: Se requieren exactamente 2 soles.");
                    continue;
                }
                Set<Integer> soles = new HashSet<>();
                for (String s : solesArray) {
                    int sol = Integer.parseInt(s);
                    if (sol < 1 || sol > 12 || !soles.add(sol)) {
                        System.err.println("ERROR: Soles fuera de rango (1-12) o repetidos.");
                        throw new InputMismatchException();
                    }
                }

                return numsStr + "|" + solesStr;

            } catch (NumberFormatException | InputMismatchException e) {
                System.err.println("ERROR: Entrada inválida. Intenta de nuevo.");
            } catch (Exception e) {
                System.out.print("¿Desea finalizar la aplicación? (S/N): ");
                if (scanner.nextLine().equalsIgnoreCase("S")) return null;
            }
        }
    }
}