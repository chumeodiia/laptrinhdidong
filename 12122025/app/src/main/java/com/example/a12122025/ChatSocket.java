package com.example.a12122025;

import io.socket.client.Socket;

import io.socket.client.IO;

public class ChatSocket {
    private static ChatSocket instance;
    private Socket socket;

    private ChatSocket() {
        try {
            socket = IO.socket("http://localhost:8080");
        } catch (Exception e) {
            e.printStackTrace();
        }
        socket.connect();
    }

    public static ChatSocket getInstance() {
        if (instance == null) instance = new ChatSocket();
        return instance;
    }

    public Socket getSocket() {
        return socket;
    }
}
