package ser12;

import java.io.*;
import java.net.*;

public class HiloServidor extends Thread {

    DataInputStream fentrada;
    Socket socket = null;

    public HiloServidor(Socket s) {
        this.socket = s;
        try {
            // CREO FLUJO DE ENTRADA
            fentrada = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("ERROR DE E/S");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ServidorChat.mensaje.setText("NUMERO DE CONEXIONES ACTUALES: "
                + ServidorChat.ACTUALES);

        // Nada más conectarse el cliente, le envío todos los mensajes
        String texto = ServidorChat.textarea.getText();
        enviarMensajes(texto);
        while (true) {
            String cadena = "*";
            try {
                // Lee lo que el cliente escribe
                cadena = fentrada.readUTF();

                // Cuando un cliente finaliza
                if (cadena.trim().equals("*")) {
                    ServidorChat.ACTUALES--;
                    ServidorChat.mensaje.setText("NUMERO DE CONEXIONES ACTUALES: "
                            + ServidorChat.ACTUALES);
                    break; // salir del while
                }

                // Aquí podrías procesar o reenviar 'cadena' a otros clientes
                ServidorChat.textarea.append(cadena + "\n");
                texto = ServidorChat.textarea.getText();


            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        // fin while

    } // fin run

    private void enviarMensajes(String texto) {
        // Recorremos tabla de sockets para enviarles los mensajes
        for (int i = 0; i < ServidorChat.CONEXIONES; i++) {
            Socket s1 = ServidorChat.tabla[i]; // obtener socket
            try {
                DataOutputStream fsalida = new DataOutputStream(s1.getOutputStream());
                fsalida.writeUTF(texto); // escribir en el socket el texto
            } catch (SocketException se) {
                // Esta excepción ocurre cuando escribimos en un socket
                // de un cliente que ha finalizado
            } catch (IOException e) {
                e.printStackTrace();
            }

                // EnviarMensajes
                // ... Fin HiloServidor

        }
    }


}



