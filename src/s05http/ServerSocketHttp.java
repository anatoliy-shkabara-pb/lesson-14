package s05http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServerSocketHttp {
    public static void main(String[] args) throws Exception {
        int port = 1234;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Сервер запущен на порту : " + port);

        // В цикле ждем запроса клиента
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Получен запрос от клиента");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            // печатаем заголовки запроса
            String headerLine;
            while((headerLine = in.readLine()).length() != 0){
                System.out.println(headerLine);
            }

            // печатает тело post запроса
            StringBuilder payload = new StringBuilder();
            while(in.ready()){
                payload.append((char) in.read());
            }
            System.out.println("\nТело запроса: " + payload);


            // пишем ответ
            out.write("HTTP/1.0 200 OK\r\n");
            out.write("Content-Type: text/html; charset=utf-8\r\n");
            // можно передать и другие заголовки
            // out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
            // out.write("Server: Apache/0.8.4\r\n");
            // out.write("Content-Length: 59\r\n");
            // out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
            // out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
            out.write("\r\n");
            out.write("<html><body><h1>");
            out.write("Ответ от сервера, текущая дата: ");
            out.write(LocalDateTime.now().toString());
            out.write("</h1></body></html>");

            System.out.println("Закрываем соединение с клиентом");
            out.close();
            in.close();
            clientSocket.close();
        }
    }
}
