package com.example.a19122025.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val title: String,
    val description: String? = null,
    val dueDate: Long? = null,
    val priority: Int = 1, // 1: Low, 2: Medium, 3: High
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)