package s04binaryclientserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Сервер запустился, ждем подключения клиента");
        Socket socket = serverSocket.accept();
        System.out.println("Клиент подключился, читаем данные от клиента");

        InputStream socketInputStream = socket.getInputStream();
        OutputStream fileOutputStream = Files.newOutputStream(
                Paths.get("files/server.txt"),
                StandardOpenOption.WRITE
        );

        byte[] buffer = new byte[1024];
        int count;
        try {
            while ((count = socketInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, count);
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения/записи данных");
            System.exit(-1);
        }
        socketInputStream.close();
        fileOutputStream.close();
        System.out.println("Готово");
    }
}
