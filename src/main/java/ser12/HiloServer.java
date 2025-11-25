package ser12;

import java.io.*;
import java.net.*;

public class HiloServer extends Thread {
    DataInputStream fentrada;
    Socket socket = null;

    public HiloServer(Socket s) {
        socket = s;
        try {
            // CREO FLUJO DE ENTRADA
            fentrada = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("ERROR DE E/S");
            e.printStackTrace();
        }
    }

    public void run() {
        ServidorChat.mensaje.setText("NUMERO DE CONEXIONES ACTUALES: " + ServidorChat.ACTUALES);
        
        // NADA MAS CONECTARSE EL CLIENTE LE ENVIO TODOS LOS MENSAJES
        String texto = ServidorChat.textarea.getText();
        EnviarMensajes(texto);

        while (true) {
            String cadena = "";
            try {
                cadena = fentrada.readUTF(); //lee lo que el cliente escribe
                
                //cuando un cliente finaliza envia un *
                if (cadena.trim().equals("*")) {
                    ServidorChat.ACTUALES--;
                    ServidorChat.mensaje.setText("NUMERO DE CONEXIONES ACTUALES: " + ServidorChat.ACTUALES);
                    break; //salir del while
                }

                ServidorChat.textarea.append(cadena + "\n");
                texto = ServidorChat.textarea.getText();
                EnviarMensajes(texto); //envio texto a todos los clientes
                
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        } // fin while
    } //run

    // ENVIA LOS MENSAJES DEL TEXTAREA A LOS CLIENTES DEL CHAT
    private void EnviarMensajes(String texto) {
        int i;
        //recorremos tabla de sockets para enviarles los mensajes
        for (i = 0; i < ServidorChat.CONEXIONES; i++) {
            Socket s1 = ServidorChat.tabla[i]; //obtener socket
            try {
                DataOutputStream fsalida = new DataOutputStream(s1.getOutputStream());
                fsalida.writeUTF(texto); //escribir en el socket el texto
            } catch (SocketException se) {
                // esta excepción ocurre cuando escribimos en un socket
                // de un cliente que ha finalizado
            } catch (IOException e) {
                e.printStackTrace();
            }
        } // for
    } // EnviarMensajes
} // .. Fin HiloServidor