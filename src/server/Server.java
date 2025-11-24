package server;

import client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    private static final int PORT = 8001;

    // Thread-safe client list for broadcasting
    public static final List<ClientHandler> clients =
            Collections.synchronizedList(new ArrayList<>());

    // ⭐ Step4: Broadcast to all clients
    public static void broadcast(String message) {
        synchronized (clients) {
            for (ClientHandler ch : clients) {
                ch.sendMessage(message);
            }
        }
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(clientSocket);
                clients.add(handler);
                handler.start();

                // ⭐ 加入可爱风欢迎词广播
                broadcast("🐱 Client " + handler.getClientId() + " joined the chat!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
