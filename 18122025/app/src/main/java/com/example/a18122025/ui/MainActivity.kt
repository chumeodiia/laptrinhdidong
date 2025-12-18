package com.example.a18122025.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.a18122025.R
import com.example.a18122025.data.DatabaseHelper
import com.example.a18122025.model.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var adapter: TaskAdapter
    private lateinit var list: ArrayList<Task>
    private var userId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DatabaseHelper(this)
        val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        userId = prefs.getInt("userId", -1)

        list = ArrayList()
        adapter = TaskAdapter(this, list, db)

        findViewById<ListView>(R.id.lvTasks).adapter = adapter
        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            val i = Intent(this, AddEditTaskActivity::class.java)
            i.putExtra("userId", userId)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    private fun loadTasks() {
        list.clear()
        val c = db.getAllTasks(userId)
        while (c.moveToNext()) {
            list.add(
                Task(
                    c.getInt(c.getColumnIndexOrThrow("task_id")),
                    c.getString(c.getColumnIndexOrThrow("title")),
                    c.getString(c.getColumnIndexOrThrow("description")),
                    c.getString(c.getColumnIndexOrThrow("date")),
                    c.getInt(c.getColumnIndexOrThrow("status")),
                    c.getInt(c.getColumnIndexOrThrow("user_id"))
                )
            )
        }
        c.close()
        adapter.notifyDataSetChanged()
    }
}
