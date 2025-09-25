package ser0;

import java.io.*;
import java.net.*;

public class Ejemplo2URL {
    public static void main(String[] args) {
        URL url = null;
        try {
            url = new URL("http://www.elaltozano.es");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        BufferedReader in = null;

        if (url != null) {
            try  {
                InputStream inputStream = url.openStream();

                in = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
