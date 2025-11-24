🐱 Cute Multi-Client Chat System (Java + JavaFX)

A cute and fully functional multi-client chat application built with Java Sockets, multithreading, and a JavaFX GUI.
This project demonstrates networking fundamentals, concurrent server design, and a polished GUI with chat bubbles and random cat avatars.

🎀 Features

🐾 Multi-client real-time chatting

🧵 Thread-per-client server model

🐱 Random cat avatar for each client

💬 Chat bubbles (WeChat-style UI)

🎨 Cute gradient theme

👉 Left/right aligned messages (self vs others)

🚀 JavaFX GUI

🖥 Console-based server

🧡 Fully scalable and concurrent

📌 Project Structure
src/
├── server/
│     ├── Server.java
│     └── ClientHandler.java
└── client/
├── Client.java             (console client)
└── CuteChatClient.java     (JavaFX GUI client)

🛠 Technologies Used

Java 22

JavaFX 22

TCP socket programming

Multithreading (Thread class)

JavaFX GUI components

OOP modular design

🎮 How to Run
1️⃣ Start the Server
Run Server.java


If successful:

Server started on port 8001

2️⃣ Run the Cute JavaFX Client
Run CuteChatClient.java


If JavaFX is required, your VM options should include:

--module-path <path_to_javafx_lib> --add-modules javafx.controls,javafx.fxml


To run multiple clients:
✔ Enable “Allow parallel run” in IntelliJ.

🎨 GUI Highlights

Soft pink gradient theme

Random cat avatars

Bubble-style chat messages

Auto scroll

Clean and modern layout

🚀 Future Improvements

Timestamp for each message

Private rooms / PM

Login system

Chat history persistence