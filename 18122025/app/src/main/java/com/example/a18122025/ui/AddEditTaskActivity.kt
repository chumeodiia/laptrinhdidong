package com.example.a18122025.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a18122025.data.DatabaseHelper
import com.example.a18122025.databinding.ActivityAddEditTaskBinding
import java.text.SimpleDateFormat
import java.util.*

class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditTaskBinding
    private lateinit var db: DatabaseHelper
    private var isEdit = false
    private var taskId = -1
    private var userId = -1
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        isEdit = intent.getBooleanExtra("isEdit", false)
        taskId = intent.getIntExtra("taskId", -1)
        userId = intent.getIntExtra("userId", -1)

        if (isEdit) loadTask()
        else setToday()

        binding.etDate.setOnClickListener { showDatePicker() }
        binding.btnSave.setOnClickListener { saveTask() }
    }

    private fun loadTask() {
        val cursor = db.getTask(taskId)
        if (cursor.moveToFirst()) {
            binding.etTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow("title")))
            binding.etDesc.setText(cursor.getString(cursor.getColumnIndexOrThrow("description")))
            binding.etDate.setText(cursor.getString(cursor.getColumnIndexOrThrow("date")))
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"))
        }
        cursor.close()
    }

    private fun setToday() {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.etDate.setText(sdf.format(calendar.time))
    }

    private fun showDatePicker() {
        DatePickerDialog(
            this,
            { _, y, m, d ->
                calendar.set(y, m, d)
                setToday()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun saveTask() {
        val title = binding.etTitle.text.toString()
        val desc = binding.etDesc.text.toString()
        val date = binding.etDate.text.toString()

        if (title.isEmpty()) {
            Toast.makeText(this, "Nhập tiêu đề", Toast.LENGTH_SHORT).show()
            return
        }

        val success = if (isEdit) {
            val cursor = db.getTask(taskId)
            cursor.moveToFirst()
            val status = cursor.getInt(cursor.getColumnIndexOrThrow("status"))
            cursor.close()
            db.updateTask(taskId, title, desc, date, status)
        } else {
            db.addTask(title, desc, date, userId)
        }

        if (success) {
            Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
