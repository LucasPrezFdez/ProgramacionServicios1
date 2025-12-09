package examenB.ej4B;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClienteCasas {
    private static final String HOST = "localhost";
    private static final int PUERTO = 6004;

    public static void main(String[] args) {
        ArrayList<Casa> casas = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Cliente Valorador de Casas ---");
        int count = 0;
        while (count < 2) {
            try {
                System.out.println("\nDatos para Casa #" + (count + 1));
                System.out.print("Número de habitaciones: ");
                int habs = scanner.nextInt();

                System.out.print("Número de baños: ");
                int banos = scanner.nextInt();

                System.out.print("¿Tiene trastero? (S/N): ");
                boolean trastero = scanner.next().trim().equalsIgnoreCase("S");

                casas.add(new Casa(habs, banos, trastero));
                count++;
            } catch (InputMismatchException e) {
                System.err.println("ERROR: Entrada inválida. Introduce solo números para habitaciones y baños.");
                scanner.nextLine();
            }
        }

        try (
                Socket cliente = new Socket(HOST, PUERTO);
                ObjectOutputStream outObjeto = new ObjectOutputStream(cliente.getOutputStream());
                DataInputStream inResultado = new DataInputStream(cliente.getInputStream());
        ) {
            System.out.println("\nEnviando " + casas.size() + " objetos Casa al servidor...");

            outObjeto.writeObject(casas);

            String valoraciones = inResultado.readUTF();
            System.out.println("\n--- VALORACIONES RECIBIDAS ---");
            System.out.println(valoraciones);

        } catch (ConnectException e) {
            System.err.println("ERROR: No se pudo conectar al servidor. Asegúrate de que el servidor esté corriendo.");
        } catch (IOException e) {
            System.err.println("ERROR de I/O: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}