package ser7;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ServidorHora {

    public static void main(String[] args) throws IOException {

        int port = 12345;
        String host = "225.0.0.1";

        MulticastSocket socket = new MulticastSocket(port);
        InetAddress grupo = InetAddress.getByName(host);

        socket.joinGroup(grupo);
        String cadena = "";


        System.out.println();
        while (true) {


                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Usar la cadena con la fecha/hora como contenido del paquete
                cadena = obtenerHoraActualYFecha();
                byte[] buf = cadena.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, grupo, port);
                socket.send(datagramPacket);
                System.out.println("Enviando: " + cadena);




        }

    }

    //metodo que nos da la hora actual

    public static String obtenerHoraActualYFecha() {
        // Obtenemos la hora actual
        LocalTime horaActual = LocalTime.now();
        LocalDate fechaActual = LocalDate.now();

        // usar yyyy en minúsculas para el año calendario
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(dtf);
        // Formateamos la hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormateada = horaActual.format(formatter);

        // Devolvemos una cadena con la fecha y la hora
        return "Fecha: " + fechaFormateada + ", Hora actual: " + horaFormateada;
    }


}
