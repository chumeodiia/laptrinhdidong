package com.example.a19122025.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.a19122025.data.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Int): LiveData<User>
}