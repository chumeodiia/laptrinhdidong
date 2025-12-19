package com.example.a19122025.data.repository

import androidx.lifecycle.LiveData
import com.example.a19122025.data.database.AppDatabase
import com.example.a19122025.data.model.Task
import com.example.a19122025.data.model.User

class TodoRepository(private val database: AppDatabase) {

    // User operations
    suspend fun register(user: User): Long {
        return database.userDao().insert(user)
    }

    suspend fun login(email: String, password: String): User? {
        return database.userDao().login(email, password)
    }

    suspend fun getUserByEmail(email: String): User? {
        return database.userDao().getUserByEmail(email)
    }

    fun getUserById(id: Int): LiveData<User> {
        return database.userDao().getUserById(id)
    }

    // Task operations
    suspend fun addTask(task: Task): Long {
        return database.taskDao().insert(task)
    }

    suspend fun updateTask(task: Task) {
        database.taskDao().update(task)
    }

    suspend fun deleteTask(task: Task) {
        database.taskDao().delete(task)
    }

    // Sửa từ Flow thành LiveData
    fun getTasksByUser(userId: Int): LiveData<List<Task>> {
        return database.taskDao().getTasksByUser(userId)
    }

    suspend fun getTaskById(taskId: Int, userId: Int): Task? {
        return database.taskDao().getTaskById(taskId, userId)
    }
}