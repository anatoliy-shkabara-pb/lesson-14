package s03simpleclientserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        System.out.println("Клиент стартовал");
        String serverIp = "127.0.0.1";
        int serverPort = 1234;
        System.out.println("Соединяемся с сервером " + serverIp + ":" + serverPort);

        Socket server = new Socket(serverIp, serverPort);
        BufferedReader inServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
        PrintWriter outServer = new PrintWriter(server.getOutputStream(), true);
        BufferedReader inConsole = new BufferedReader(new InputStreamReader(System.in));

        String dataFromUser, dataFromServer;

        // Основной цикл отправки сообщений серверу
        while ((dataFromUser = inConsole.readLine()) != null) {
            outServer.println(dataFromUser);
            dataFromServer = inServer.readLine();
            System.out.println(dataFromServer);
            if ("exit".equalsIgnoreCase(dataFromUser)) {
                break;
            }
        }
        outServer.close();
        inServer.close();
        outServer.close();
        server.close();
    }
}
