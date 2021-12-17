package s04binaryclientserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.*;

/**
 * Предположим, клиент должен отправить серверу содержимое файла.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Path filePath = Paths.get("files/client.txt");
        InputStream fileInputStream = Files.newInputStream(
                filePath,
                StandardOpenOption.READ
        );

        Socket socket = new Socket("localhost", 1234);
        OutputStream serverOutputStream = socket.getOutputStream();

        System.out.println("Отправка файла " + filePath.getFileName() +
                " на " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());

        byte[] buffer = new byte[1024];
        int count;
        while ((count = fileInputStream.read(buffer)) > 0) {
            serverOutputStream.write(buffer, 0, count);
        }

        fileInputStream.close();
        serverOutputStream.close();
        System.out.println("готово");
    }
}
