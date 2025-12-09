package examenB.ej4B;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServidorCasas {
    private static final int PUERTO = 6004;

    private static final int VALOR_HABITACION = 75;
    private static final int VALOR_BANO = 50;
    private static final int VALOR_TRASTERO = 100;

    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(PUERTO);
        System.out.println("Servidor de Valoración de Casas iniciado en puerto " + PUERTO + "...");

        while (true) {
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado. Calculando valoración de casas...");

            try (
                    ObjectInputStream inObjeto = new ObjectInputStream(cliente.getInputStream());
                    DataOutputStream outResultado = new DataOutputStream(cliente.getOutputStream());
            ) {
                @SuppressWarnings("unchecked")
                ArrayList<Casa> casas = (ArrayList<Casa>) inObjeto.readObject();
                String resultado = calcularValoraciones(casas);
                outResultado.writeUTF(resultado);
                System.out.println("Valoración enviada al cliente. Casas valoradas: " + casas.size());

            } catch (EOFException | SocketException e) {
                System.out.println("Conexión con cliente finalizada.");
            } catch (ClassNotFoundException e) {
                System.err.println("ERROR: No se encontró la clase Casa.");
            } catch (IOException e) {
                System.err.println("Error de I/O: " + e.getMessage());
            } finally {
                cliente.close();
            }
        }
    }

    private static String calcularValoraciones(ArrayList<Casa> casas) {
        StringBuilder sb = new StringBuilder();
        int totalCasas = 0;

        for (Casa c : casas) {
            totalCasas++;
            int valor = 0;
            valor += c.getNumHabitaciones() * VALOR_HABITACION;
            valor += c.getNumBanos() * VALOR_BANO;
            if (c.tieneTrastero()) {
                valor += VALOR_TRASTERO;
            }

            String trasteroStr = c.tieneTrastero() ? "con trastero" : "sin trastero";

            sb.append(String.format("El alquiler de la casa con %d habitaciones, %d baño y %s vale: %d€\n",
                    c.getNumHabitaciones(), c.getNumBanos(), trasteroStr, valor));
        }

        sb.append(String.format("\nTotal de casas valoradas: %d", totalCasas));

        return sb.toString();
    }
}