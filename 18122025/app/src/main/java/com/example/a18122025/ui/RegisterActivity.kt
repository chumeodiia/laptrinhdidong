package com.example.a18122025.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a18122025.R
import com.example.a18122025.data.DatabaseHelper

class RegisterActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        db = DatabaseHelper(this)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.tvLogin)

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(
                    this,
                    "Vui lòng nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (username.length < 4) {
                Toast.makeText(
                    this,
                    "Tên đăng nhập phải có ít nhất 4 ký tự",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(
                    this,
                    "Mật khẩu phải có ít nhất 6 ký tự",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(
                    this,
                    "Mật khẩu xác nhận không khớp",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (db.registerUser(username, password)) {
                Toast.makeText(
                    this,
                    "Đăng ký thành công!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Tên đăng nhập đã tồn tại!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        tvLogin.setOnClickListener {
            finish()
        }
    }
}
