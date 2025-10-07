package ser3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerPalo {

    public static void main(String[] args) throws IOException {

        int puerto = 6000;

        ServerSocket serverSocket = new ServerSocket(puerto);
        System.out.println("Servidor inicializado en el puerto: " + puerto);

        Socket socket = serverSocket.accept();
        System.out.println("Se acaba de conectar un jitano");

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        boolean sigue = true;
        try {
            while (sigue) {
                String frase = dataInputStream.readUTF();

                if (!frase.equals("exit")) {
                    System.out.println("Recibido: " + frase);
                    if (esPalindromo(frase)){
                        System.out.println("La frase es palindroma");
                        dataOutputStream.writeUTF("La frase es palindroma");
                    } else dataOutputStream.writeUTF("La frase no es palindroma");
                    System.out.println();
                    System.out.println("Iniciando otro bucle");
                } else sigue = false;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static boolean esPalindromo(String frase) {

        StringBuilder sb = new StringBuilder();
        sb.append(frase);


        if (sb == sb.reverse())
            return true;
        return false;
    }
}
