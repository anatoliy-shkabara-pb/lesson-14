package s01inet;

import java.net.InetAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        InetAddress addr = InetAddress.getByName("www.google.com");
        // выводим IP-адрес
        System.out.println(addr.getHostAddress());

        // Для работы на локальном компьютере можно использовать IP-адрес 127.0.0.1
        // или имя “localhost” или null.
        addr = InetAddress.getByName("localhost");
        System.out.println(addr.getHostAddress());
    }
}
