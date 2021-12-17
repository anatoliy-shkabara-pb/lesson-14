package s06multithreadserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMultiThread {

    static class Handler implements Runnable {
        private final Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + ": Получен запрос от клиента");

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

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

                // имитация долгой обработки запроса
                int sec = 10;
                System.out.println("Ждем " + sec + " секунд имитируя долгу обработку");
                Thread.sleep(sec * 1000);

                // пишем ответ
                out.write("HTTP/1.0 200 OK\r\n");
                out.write("Content-Type: text/html; charset=utf-8\r\n");
                out.write("\r\n");
                out.write("<html><body><h1>");
                out.write("Ответ от сервера, текущая дата: ");
                out.write(LocalDateTime.now().toString());
                out.write("</h1></body></html>");

                System.out.println("Закрываем соединение с клиентом");
                out.close();
                in.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (Exception ex) {
                    // ignore
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 1234;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Сервер запущен на порту : " + port);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        // В цикле ждем запроса клиента
        while (true) {
            Socket clientSocket = serverSocket.accept();
            threadPool.submit(new Handler(clientSocket));
        }
    }
}
