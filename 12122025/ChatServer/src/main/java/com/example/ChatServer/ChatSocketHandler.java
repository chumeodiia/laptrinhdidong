/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ChatServer;

import org.springframework.stereotype.Component;

/**
 *
 * @author duytu
 */
@Component
public class ChatSocketHandler {

    @OnConnect
    public void onConnect(SocketIOClient client) {
        System.out.println("Client connected: " + client.getSessionId());
    }

    @OnEvent("join_room")
    public void onJoinRoom(SocketIOClient client, String room) {
        client.joinRoom(room);
    }

    @OnEvent("send_message")
    public void onSendMessage(SocketIOClient client, ChatMessage msg) {
        // Gửi cho manager
        client.getNamespace().getRoomOperations("manager_room")
                .sendEvent("receive_message", msg);
    }

    @OnEvent("manager_reply")
    public void onManagerReply(SocketIOClient client, ChatMessage msg) {
        // Gửi cho khách hàng
        client.getNamespace().getRoomOperations(msg.getUserId())
                .sendEvent("receive_message", msg);
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        System.out.println("Client disconnected");
    }
}
