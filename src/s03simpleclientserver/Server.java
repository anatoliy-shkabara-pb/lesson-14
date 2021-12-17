package s03simpleclientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("Старт сервера");
        int serverPort = 1234;

        // поток для чтения данных
        BufferedReader in = null;
        // поток для отправки данных
        PrintWriter out = null;

        // серверный сокет
        ServerSocket server = null;
        // сокет для обслуживания клиента
        Socket clientSocket = null;

        // создаем серверный сокет
        try {
            server = new ServerSocket(serverPort);
        } catch (IOException e) {
            System.out.println("Ошибка связывания с портом 1234");
            System.exit(-1);
        }

        try {
            System.out.println("Ждем соединения");
            clientSocket = server.accept();
            System.out.println("Клиент подключился");
        } catch (IOException e) {
            System.out.println("Не могу установить соединение");
            System.exit(-1);
        }

        // создаем потоки для связи с клиентом
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        String clientMessage;

        // цикл ожидания сообщений от клиента
        System.out.println("Ожидаем сообщений");
        while ((clientMessage = in.readLine()) != null) {
            if ("exit".equalsIgnoreCase(clientMessage)) {
                break;
            }
            out.println("Сервер: " + clientMessage);
            System.out.println(clientMessage);
        }

        // Закрываем все соединения
        out.close();
        in.close();
        clientSocket.close();
        server.close();
    }
}
