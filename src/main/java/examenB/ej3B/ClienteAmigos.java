package examenB.ej3B;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClienteAmigos {
    private static final String HOST = "localhost";
    private static final int PUERTO = 6003;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (
                Socket cliente = new Socket(HOST, PUERTO);
                DataOutputStream fsalida = new DataOutputStream(cliente.getOutputStream());
                DataInputStream fentrada = new DataInputStream(cliente.getInputStream());
        ) {
            System.out.println("Cliente Amigos conectado al servidor.");
            boolean repetir = true;

            while (repetir) {
                int[] nums = pedirYFiltrarNumeros(scanner);

                if (nums == null) {
                    fsalida.writeUTF("0");
                    repetir = false;
                    System.out.println("Finalizando aplicación...");
                    continue;
                }

                String mensaje = nums[0] + "," + nums[1];
                fsalida.writeUTF(mensaje);

                String respuesta = fentrada.readUTF();
                System.out.println("Resultado del Servidor: " + respuesta);

                System.out.print("¿Desea preguntar por un nuevo par de números? (S/N): ");
                String respuestaRepetir = scanner.next().trim();
                if (!respuestaRepetir.equalsIgnoreCase("S")) {
                    fsalida.writeUTF("0");
                    repetir = false;
                }
                scanner.nextLine();
            }

        } catch (ConnectException e) {
            System.err.println("ERROR: No se pudo conectar al servidor. Asegúrate de que el servidor esté corriendo.");
        } catch (IOException e) {
            System.err.println("ERROR de I/O: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static int[] pedirYFiltrarNumeros(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Introduce el primer número (entero positivo o '0' para salir): ");
                int num1 = scanner.nextInt();
                if (num1 == 0) return null;

                System.out.print("Introduce el segundo número (entero positivo): ");
                int num2 = scanner.nextInt();

                if (num1 > 0 && num2 > 0) {
                    return new int[]{num1, num2};
                } else {
                    System.err.println("ERROR: Ambos números deben ser enteros y positivos.");
                }
            } catch (InputMismatchException e) {
                System.err.println("ERROR: Entrada inválida. Introduce solo números enteros.");
                scanner.nextLine();
            }
        }
    }
}