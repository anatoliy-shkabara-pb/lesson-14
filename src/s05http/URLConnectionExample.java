package s05http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class URLConnectionExample {
    public static void main(String[] args) throws Exception {
        URL myUrl = new URL("https://www.oracle.com");
        URLConnection conn = myUrl.openConnection();
        conn.getHeaderFields();

        // Код ответа
        System.out.println("Код ответа: " + ((HttpURLConnection) conn).getResponseCode());

        // Получение всех заголовков ответа
        Map<String, List<String>> map = conn.getHeaderFields();
        System.out.println("Заголовки ответа: ");
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Получить длину содержимого
        long length = conn.getContentLengthLong();
        if(length == -1) {
            System.out.println("Длина содержимого недоступна");
        } else {
            System.out.println("Длина содержимого: " + length);
        }

        if(length != 0) {
            System.out.println("=== Содержимое ===");
            InputStream inputStream = conn.getInputStream();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    System.out.println(inputLine);
            }
        } else {
            System.out.println("Содержимое недоступно.");
        }
    }
}
