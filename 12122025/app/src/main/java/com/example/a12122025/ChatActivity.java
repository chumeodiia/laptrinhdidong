package com.example.a12122025;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import io.socket.client.Socket;

public class ChatActivity extends AppCompatActivity {

    Socket socket;
    EditText editText = findViewById(R.id.edtMessage);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        socket = ChatSocket.getInstance().getSocket();

        // Tham gia room của khách
        socket.emit("join_room", "customer123");

        // Nhận tin nhắn phản hồi từ manager
        socket.on("receive_message", args -> {
            JSONObject msg = (JSONObject) args[0];
            runOnUiThread(() -> {
                // cập nhật UI
            });
        });

        findViewById(R.id.btnSend).setOnClickListener(v -> {
            String text = editText.getText().toString();

            JSONObject data = new JSONObject();
            try {
                data.put("userId", "customer123");
                data.put("message", text);
            } catch (Exception ignored) {}

            socket.emit("send_message", data);
        });
    }
}