package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class CuteChatClient extends Application {

    private VBox messageBox;
    private TextField inputField;

    private DataInputStream in;
    private DataOutputStream out;

    private final String SERVER_ADDRESS = "localhost";
    private final int SERVER_PORT = 8001;

    private String myAvatar;
    private final String[] avatars = {"🐱", "🐈", "😺", "😸", "😻", "😽", "😼", "😹"};

    @Override
    public void start(Stage stage) {


        myAvatar = avatars[new Random().nextInt(avatars.length)];

        stage.setTitle(myAvatar + " Cute Chat Client");

        // --- Scrollable Chat Bubble Area ---
        messageBox = new VBox(10);
        messageBox.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(messageBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: linear-gradient(to bottom right, #FFE6F2, #FFF6FF);");

        // --- Input Area ---
        inputField = new TextField();
        inputField.setPromptText("speak something ~");
        inputField.setStyle("-fx-font-size: 14px;");

        Button sendButton = new Button("submit");
        sendButton.setStyle("-fx-font-size: 14px; -fx-background-color: pink; -fx-border-radius: 8; -fx-background-radius: 8;");

        sendButton.setOnAction(e -> sendMessage());
        inputField.setOnAction(e -> sendMessage());

        HBox bottomBox = new HBox(10, inputField, sendButton);
        bottomBox.setPadding(new Insets(10));

        VBox root = new VBox(10, scrollPane, bottomBox);
        root.setStyle("-fx-background-color: #FFF0F8;");

        stage.setScene(new Scene(root, 520, 450));
        stage.show();

        connectToServer();
    }

    private void sendMessage() {
        String message = inputField.getText();
        if (message.isEmpty()) return;

        try {
            out.writeUTF(message);
        } catch (IOException e) {
            addSystemMessage("❌ Failed to send message");
        }
        addMyBubble(myAvatar + " " + message);

        inputField.clear();
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    String msg = in.readUTF();
                    addOtherBubble(msg);
                }

            } catch (IOException e) {
                addSystemMessage("❌ lose server");
            }
        }).start();
    }

    // --- UI Helpers:  ---
    private void addMyBubble(String text) {
        Platform.runLater(() -> {
            HBox container = new HBox();
            container.setPadding(new Insets(5));
            container.setAlignment(Pos.CENTER_RIGHT);

            Label bubble = new Label(text);
            bubble.setStyle("-fx-background-color: #FFB6C1; -fx-background-radius: 12; -fx-padding: 8; -fx-font-size: 14px; -fx-text-fill: white;");

            container.getChildren().add(bubble);
            messageBox.getChildren().add(container);
        });
    }

    // --- UI Helpers: ---
    private void addOtherBubble(String text) {
        Platform.runLater(() -> {
            HBox container = new HBox();
            container.setPadding(new Insets(5));
            container.setAlignment(Pos.CENTER_LEFT);


            String avatar = avatars[new Random().nextInt(avatars.length)];
            Label bubble;

            if (text.startsWith("Client")) {
                bubble = new Label(avatar + " " + text);
            } else {
                bubble = new Label(text);
            }

            bubble.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 8; -fx-font-size: 14px;");

            container.getChildren().add(bubble);
            messageBox.getChildren().add(container);
        });
    }

    private void addSystemMessage(String text) {
        Platform.runLater(() -> {
            HBox box = new HBox();
            box.setAlignment(Pos.CENTER);
            Label label = new Label(text);
            label.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 14px;");
            box.getChildren().add(label);
            messageBox.getChildren().add(box);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}