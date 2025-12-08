package examenA.ej1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadClient extends Thread {

    private BufferedReader in;
    private Socket socket;

    public ThreadClient(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo del servidor: " + e.getMessage());
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException ignored) {}
        }
    }
}

