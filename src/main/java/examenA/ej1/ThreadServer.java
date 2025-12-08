package examenA.ej1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ThreadServer extends Thread {

    // Mapa compartido (username -> PrintWriter) para enviar mensajes a todos los clientes
    private static final Map<String, PrintWriter> clients = new HashMap<>();

    private BufferedReader fentrada;
    private PrintWriter fsalida;
    private Socket socket;
    private String username;

    public ThreadServer(Socket socket) throws IOException {
        this.socket = socket;
        fentrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        fsalida = new PrintWriter(socket.getOutputStream(), true);
    }



    public void run() {

        try {
            // Primero recibimos el nombre de usuario
            String inputLine = fentrada.readLine();
            if (inputLine == null || inputLine.trim().isEmpty()) {
                fsalida.println("Nombre de usuario inválido. Conexión cerrada.");
                closeEverything();
                return;
            }
            username = inputLine.trim();

            // Si ya existe el usuario, rechazamos
            if (clients.containsKey(username)) {
                fsalida.println("Usuario ya conectado. Elija otro nombre.");
                closeEverything();
                return;
            }

            // Registramos al cliente
            clients.put(username, fsalida);
            broadcast("[Servidor] " + username + " se ha unido al chat.");
            System.out.println(username + " conectado desde " + socket.getRemoteSocketAddress());

            // Escuchamos mensajes del cliente y los retransmitimos
            while ((inputLine = fentrada.readLine()) != null) {
                String msg = inputLine.trim();
                if (msg.isEmpty()) continue;
                else {
                    // Si el cliente envía /quit cerramos su conexión
                    if ("/quit".equalsIgnoreCase(msg)) {
                        break;
                    }
                    String mensajeCompleto = username + ": " + msg;
                    System.out.println(mensajeCompleto); // mostrar en consola del servidor
                    broadcast(mensajeCompleto);
                }

            }

        } catch (IOException e) {
            System.err.println("Error en hilo cliente " + username + ": " + e.getMessage());
        } finally {
            // Limpieza al desconectar
            if (username != null && clients.containsKey(username)) {
                clients.remove(username);
                broadcast("[Servidor] " + username + " ha abandonado el chat.");
                System.out.println(username + " desconectado.");
            }
            try {
                closeEverything();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    // Enviar mensaje a todos los clientes conectados
    private void broadcast(String message) {
        for (PrintWriter pw : clients.values()) {
            pw.println(message);
        }
    }

    private void closeEverything() throws IOException {
        try {
            if (fentrada != null) fentrada.close();
        } catch (IOException e) {
            // ignore
        }
        if (fsalida != null) fsalida.close();
        if (socket != null && !socket.isClosed()) socket.close();
    }

}
