package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";
        final int SERVER_PORT = 8001;

        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to server!");

            // Input/output streams
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Thread A → Listen for messages from server
            Thread receiver = new Thread(() -> {
                try {
                    while (true) {
                        String msg = in.readUTF();
                        System.out.println("\n💬 Server says: " + msg + "\n> ");
                    }
                } catch (IOException e) {
                    System.out.println("Server connection closed.");
                }
            });
            receiver.start();

            // Thread B → Send messages to server
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("quit")) {
                    out.writeUTF("QUIT");
                    break;
                }

                out.writeUTF(input);
            }

            socket.close();
            System.out.println("Disconnected.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
