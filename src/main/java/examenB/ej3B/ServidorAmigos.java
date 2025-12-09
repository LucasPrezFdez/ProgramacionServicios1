package examenB.ej3B;

import java.io.*;
import java.net.*;

public class ServidorAmigos {
    private static final int PUERTO = 6003;

    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(PUERTO);
        System.out.println("Servidor de Amigos iniciado en puerto " + PUERTO + "...");

        while (true) {
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado.");

            DataInputStream fentrada = new DataInputStream(cliente.getInputStream());
            DataOutputStream fsalida = new DataOutputStream(cliente.getOutputStream());

            try {
                while (true) {
                    String input = fentrada.readUTF();
                    if (input.equals("0")) break;

                    String[] numsStr = input.split(",");
                    if (numsStr.length != 2) {
                        fsalida.writeUTF("ERROR: El servidor esperaba dos números.");
                        continue;
                    }

                    int num1 = Integer.parseInt(numsStr[0].trim());
                    int num2 = Integer.parseInt(numsStr[1].trim());

                    String resultado = comprobarAmigos(num1, num2);

                    fsalida.writeUTF(resultado);
                }
            } catch (EOFException | SocketException e) {
                System.out.println("Conexión con cliente finalizada.");
            } catch (IOException e) {
                System.err.println("Error de I/O con el cliente: " + e.getMessage());
            } finally {
                fsalida.close();
                fentrada.close();
                cliente.close();
            }
        }
    }

    private static String comprobarAmigos(int a, int b) {

        if (a <= 0 || b <= 0) {
            return "ERROR: Ambos números deben ser positivos.";
        }
        if (a == b) {
            return a + " y " + b + " no son amigos (no son divisores propios entre sí).";
        }

        long sumaDivisoresA = sumaDivisoresPropios(a);
        long sumaDivisoresB = sumaDivisoresPropios(b);

        if (sumaDivisoresA == b && sumaDivisoresB == a) {
            return a + " y " + b + " SON NÚMEROS AMIGOS.";
        } else {
            return String.format("%d y %d no son amigos. (Suma divisores de %d: %d | Suma divisores de %d: %d)",
                    a, b, a, sumaDivisoresA, b, sumaDivisoresB);
        }
    }

    private static long sumaDivisoresPropios(int num) {
        if (num <= 1) return 0;
        long suma = 1;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                suma += i;
                if (i * i != num) {
                    suma += num / i;
                }
            }
        }
        return suma;
    }
}