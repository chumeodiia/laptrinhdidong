package com.example.a18122025.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = """
            CREATE TABLE users (
                user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE,
                password TEXT
            )
        """.trimIndent()

        val createTasksTable = """
            CREATE TABLE tasks (
                task_id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT,
                description TEXT,
                date TEXT,
                status INTEGER,
                user_id INTEGER,
                FOREIGN KEY(user_id) REFERENCES users(user_id)
            )
        """.trimIndent()

        db.execSQL(createUsersTable)
        db.execSQL(createTasksTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS tasks")
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun registerUser(username: String, password: String): Boolean {
        val values = ContentValues().apply {
            put("username", username)
            put("password", password)
        }
        return writableDatabase.insert("users", null, values) != -1L
    }

    fun loginUser(username: String, password: String): Boolean {
        val cursor = readableDatabase.query(
            "users",
            arrayOf("user_id"),
            "username=? AND password=?",
            arrayOf(username, password),
            null, null, null
        )
        val result = cursor.count > 0
        cursor.close()
        return result
    }

    fun getUserId(username: String): Int {
        val cursor = readableDatabase.query(
            "users",
            arrayOf("user_id"),
            "username=?",
            arrayOf(username),
            null, null, null
        )
        var id = -1
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0)
        }
        cursor.close()
        return id
    }

    fun addTask(title: String, desc: String, date: String, userId: Int): Boolean {
        val values = ContentValues().apply {
            put("title", title)
            put("description", desc)
            put("date", date)
            put("status", 0)
            put("user_id", userId)
        }
        return writableDatabase.insert("tasks", null, values) != -1L
    }

    fun updateTask(taskId: Int, title: String, desc: String, date: String, status: Int): Boolean {
        val values = ContentValues().apply {
            put("title", title)
            put("description", desc)
            put("date", date)
            put("status", status)
        }
        return writableDatabase.update(
            "tasks", values, "task_id=?",
            arrayOf(taskId.toString())
        ) > 0
    }

    fun deleteTask(taskId: Int): Boolean {
        return writableDatabase.delete(
            "tasks", "task_id=?",
            arrayOf(taskId.toString())
        ) > 0
    }

    fun getAllTasks(userId: Int): Cursor =
        readableDatabase.query(
            "tasks",
            null,
            "user_id=?",
            arrayOf(userId.toString()),
            null, null,
            "date DESC"
        )

    fun getTask(taskId: Int): Cursor =
        readableDatabase.query(
            "tasks",
            null,
            "task_id=?",
            arrayOf(taskId.toString()),
            null, null, null
        )

    companion object {
        private const val DATABASE_NAME = "TodoList.db"
        private const val DATABASE_VERSION = 1
    }
}
