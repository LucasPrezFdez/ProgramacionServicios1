package ser0;

import java.net.MalformedURLException;
import java.net.URL;

public class Ejemplo1URLmio {

    public static void main(String[] args) {
        try {
            System.out.println("Constructor simple para una URL:");
            URL url = new URL("https://www.wikipedia.org/");
            visualiza(url);

            System.out.println("Otro constructor simple para una URL:");
            url = new URL("https://www.ejemplo.com/productos/lista?cat=5");
            visualiza(url);

            System.out.println("Constructor para protocolo + URL + directorio:");
            url = new URL("https", "www.misitio.net", "/blog/articulo");
            visualiza(url);

            System.out.println("Constructor para protocolo + URL + puerto + directorio:");
            url = new URL("https", "minecraft.com", 443, "/download");
            visualiza(url);

            System.out.println("Constructor para un objeto URL y un directorio:");
            URL urlBase = new URL("https://www.tiendaonline.com/");
            url = new URL(urlBase, "/ofertas/2025/tecnologia");
            visualiza(url);

        } catch (MalformedURLException e) {
            System.out.println(e);
        }
    }

    private static void visualiza(URL url) {
        System.out.println("\tURL completa: " + url.toString());
        System.out.println("\tgetProtocol(): " + url.getProtocol());
        System.out.println("\tgetHost(): " + url.getHost());
        System.out.println("\tgetPort(): " + url.getPort());
        System.out.println("\tgetFile(): " + url.getFile());
        System.out.println("\tgetUserInfo(): " + url.getUserInfo());
        System.out.println("\tgetPath(): " + url.getPath());
        System.out.println("\tgetAuthority(): " + url.getAuthority());
        System.out.println("\tgetQuery(): " + url.getQuery());
        System.out.println("=====");
    }

}
