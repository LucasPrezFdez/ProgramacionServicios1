package ser12;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ServidorChat extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    static ServerSocket servidor;
    static final int PUERTO = 44444;  // puerto por el que escucha

    static int CONEXIONES = 0;        // cuenta las conexiones
    static int ACTUALES = 0;          // nº de conexiones actuales activas
    static int MAXIMO = 10;           // máximo de conexio


    static JTextField mensaje = new JTextField("*");
    static JTextField mensaje2 = new JTextField("*");

    private JScrollPane scrollpanel;
    static JTextArea textarea;

    JButton salir = new JButton("Salir");

    static Socket tabla[] = new Socket[10]; // almacena sockets de clientes


    // Constructor
    public ServidorChat() {
        super("VENTANA DEL SERVIDOR DE CHAT *");

        setLayout(null);

        mensaje.setBounds(10, 10, 400, 30);
        add(mensaje);
        mensaje.setEditable(false);
        mensaje2.setBounds(10, 40, 400, 30);
        add(mensaje2);
        mensaje2.setEditable(false);

        textarea = new JTextArea();
        scrollpanel = new JScrollPane(textarea);
        scrollpanel.setBounds(10, 80, 400, 300);
        add(scrollpanel);

        salir.setBounds(420, 10, 100, 30);
        add(salir);

        textarea.setEditable(false);
        salir.addActionListener(this);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }

    // Acción cuando pulsamos botón salir
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == salir) { // SE PULSA SALIR
            try {
                servidor.close(); // cierro
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0); // fin
        }
    }

    public static void main(String args[])
            throws IOException {

        servidor = new ServerSocket(PUERTO);
        System.out.println("Servidor iniciado...");

        ServidorChat pantalla = new ServidorChat();
        pantalla.setBounds(0, 0, 540, 400);
        pantalla.setVisible(true);

        mensaje.setText("NUMERO DE CONEXIONES ACTUALES: " + 0);
        // SE ADMITEN HASTA 10 CONEXIONES
        while (CONEXIONES < MAXIMO) {
            Socket s = new Socket();
            try {
                s = servidor.accept(); // esperando cliente
                // aquí iría la lógica para manejar la conexión
            } catch (SocketException ns) {
                // sale por aquí si pulsamos botón salir
                // no se ejecuta todo el bucle
                break; // salir del bucle
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            tabla[CONEXIONES] = s; //almacenar socket
            CONEXIONES++;
            ACTUALES++;

            HiloServidor hilo = new HiloServidor(s);
            hilo.start(); // lanzar hilo


            // fin while
        }
        // Cuando finaliza bucle
        // Cerrar servidor si no se ha cerrado antes
        // Sale cuando se llega al máximo de conexiones

        mensaje.setForeground(Color.RED);
        mensaje2.setText("NUMERO DE CONEXIONES ESTABLECIDAS: " + CONEXIONES);

        try {
            servidor.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("Servidor finalizado.");

        // Fin main
        // Fin ServidorChat


    }


}


