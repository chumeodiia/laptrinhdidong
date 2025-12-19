package com.example.a19122025

import android.app.Application
import com.example.a19122025.data.database.AppDatabase
import com.example.a19122025.data.repository.TodoRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApplication : Application() {
    // Sử dụng lazy initialization
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { TodoRepository(database) }
}