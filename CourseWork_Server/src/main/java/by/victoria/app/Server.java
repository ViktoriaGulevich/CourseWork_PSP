package by.victoria.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(2525)) {
            System.out.println("The multi-threaded server has started");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.printf("New connection established\n\tHis data: IP: %s:%s\n",
                        clientSocket.getInetAddress(),
                        clientSocket.getPort());
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't launch server", e);
        }
    }
}
