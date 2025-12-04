package com.example.a04122025_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.bumptech.glide.Glide;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imgAvatar;
    private TextView tvMaId, tvTenDangNhap, tvHoTen, tvEmail, tvGioiTinh;
    private MaterialButton btnDangXuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
        loadUserData();
        setupListeners();
    }

    private void initViews() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        imgAvatar = findViewById(R.id.imgAvatar);
        tvMaId = findViewById(R.id.tvMaId);
        tvTenDangNhap = findViewById(R.id.tvTenDangNhap);
        tvHoTen = findViewById(R.id.tvHoTen);
        tvEmail = findViewById(R.id.tvEmail);
        tvGioiTinh = findViewById(R.id.tvGioiTinh);
        btnDangXuat = findViewById(R.id.btnDangXuat);
    }

    private void loadUserData() {
        // Load dữ liệu từ SharedPreferences hoặc API
        tvMaId.setText("3");
        tvTenDangNhap.setText("trung1");
        tvHoTen.setText("Nguyễn Hữu Trung");
        tvEmail.setText("trung2@gmail.com");
        tvGioiTinh.setText("Male");

        // Load avatar nếu có
        String avatarUrl = ""; // URL avatar từ server
        if (!avatarUrl.isEmpty()) {
            Glide.with(this)
                    .load(avatarUrl)
                    .circleCrop()
                    .into(imgAvatar);
        }
    }

    private void setupListeners() {
        imgAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ChangeAvatarActivity.class);
            intent.putExtra("openChangeAvatar", true);
            startActivity(intent);
        });

        btnDangXuat.setOnClickListener(v -> {
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}