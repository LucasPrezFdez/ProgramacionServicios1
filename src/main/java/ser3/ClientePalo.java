package ser3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientePalo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String host = "localhost";
        int port = 6000;

        try {

            Socket cliente = new Socket(host,port);

            System.out.println("Conection data");
            System.out.println("Port: "+cliente.getPort());
            System.out.println("Host: "+cliente.getInetAddress().getHostName());
            System.out.println("Host remote ip: "+cliente.getInetAddress().toString());


            boolean keep = true;
            while (keep) {
                DataOutputStream dataOutputStream = new DataOutputStream(cliente.getOutputStream());
                DataInputStream dataInputStream = new DataInputStream(cliente.getInputStream());
                System.out.println("Enter a sentence");
                String sentence = sc.nextLine();

                dataOutputStream.writeUTF(sentence);

                if (!sentence.equals("exit")){
                    String msg = dataInputStream.readUTF();
                    System.out.println("Message received: " + msg);
                    System.out.println("=========================================================");
                } else {
                    System.out.println("Quiting...");
                    keep = false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
