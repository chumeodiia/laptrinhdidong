package com.example.a18122025.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a18122025.R
import com.example.a18122025.data.DatabaseHelper


class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var db: DatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = DatabaseHelper(this)
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        // Kiểm tra đã đăng nhập chưa
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Vui lòng nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (db.loginUser(username, password)) {
                val userId = db.getUserId(username)

                sharedPreferences.edit().apply {
                    putBoolean("isLoggedIn", true)
                    putString("username", username)
                    putInt("userId", userId)
                    apply()
                }

                Toast.makeText(
                    this,
                    "Đăng nhập thành công!",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Tên đăng nhập hoặc mật khẩu không đúng!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}