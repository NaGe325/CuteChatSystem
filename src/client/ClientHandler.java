package client;

import java.io.*;
import java.util.Scanner;
import java.net.Socket;

public class ClientHandler extends Thread {

    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private boolean running = true;
    private static int clientCounter = 0;
    private int clientId;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try{
            synchronized (ClientHandler.class) {
                clientCounter++;
                clientId = clientCounter;
        }
            System.out.println("🐱 Client " + clientId + " connected!");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Send message to this client (for broadcasting)
    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            System.out.println("Failed to send message to Client " + clientId);
        }
    }
    // Return this client's ID
    public int getClientId() {
        return clientId;
    }
    @Override
    public void run() {
        try {
            while (running) {
                String message = in.readUTF();
                System.out.println("🐱 Client " + clientId + " says: " + message);
                server.Server.broadcast("🐱 Client " + clientId + " says: " + message);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + socket.getInetAddress());
        } finally {
            server.Server.clients.remove(this);
            server.Server.broadcast("🐱 Client " + clientId + " left the chat.");}
            {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
