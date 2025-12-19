package com.example.a19122025.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a19122025.data.model.Task
import com.example.a19122025.data.repository.TodoRepository
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TodoRepository,
    private val userId: Int
) : ViewModel() {

    // Sửa: Không dùng asLiveData()
    val tasks: LiveData<List<Task>> = repository.getTasksByUser(userId)

    // Sửa thành nullable để có thể set null khi clear
    private val _taskOperationStatus = MutableLiveData<Boolean?>()
    val taskOperationStatus: LiveData<Boolean?> = _taskOperationStatus

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun addTask(title: String, description: String?, dueDate: Long?, priority: Int) {
        if (title.isEmpty()) {
            _errorMessage.value = "Title cannot be empty"
            return
        }

        viewModelScope.launch {
            try {
                val task = Task(
                    userId = userId,
                    title = title,
                    description = description,
                    dueDate = dueDate,
                    priority = priority
                )
                val taskId = repository.addTask(task)
                _taskOperationStatus.postValue(taskId > 0)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to add task: ${e.message}")
                _taskOperationStatus.postValue(false)
            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                repository.updateTask(task)
                _taskOperationStatus.postValue(true)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to update task: ${e.message}")
                _taskOperationStatus.postValue(false)
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                repository.deleteTask(task)
                _taskOperationStatus.postValue(true)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to delete task: ${e.message}")
                _taskOperationStatus.postValue(false)
            }
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            try {
                val updatedTask = task.copy(
                    isCompleted = !task.isCompleted,
                    updatedAt = System.currentTimeMillis()
                )
                repository.updateTask(updatedTask)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to update task: ${e.message}")
            }
        }
    }

    fun clearStatus() {
        _taskOperationStatus.value = null
        _errorMessage.value = null
    }
}