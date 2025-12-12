/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ChatServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatApplication implements CommandLineRunner {

    @Autowired
    SocketIOServer server;

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Override
    public void run(String... args) {
        server.start();
    }
}
