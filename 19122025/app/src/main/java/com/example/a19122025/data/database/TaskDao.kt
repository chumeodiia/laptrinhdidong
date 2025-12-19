package com.example.a19122025.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.a19122025.data.model.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks WHERE userId = :userId ORDER BY createdAt DESC")
    fun getTasksByUser(userId: Int): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :taskId AND userId = :userId")
    suspend fun getTaskById(taskId: Int, userId: Int): Task?
}