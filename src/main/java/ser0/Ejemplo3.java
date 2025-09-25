package ser0;

import java.io.*;
import java.net.*;

public class Ejemplo3 {
    public static void main(String[] args) {
        URL url;
        URLConnection urlCon;
        try {
            url = new URL("http://www.elaltozano.es");
            urlCon = url.openConnection();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlCon.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
