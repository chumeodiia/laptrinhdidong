package com.example.a19122025.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a19122025.data.model.User
import com.example.a19122025.data.repository.TodoRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: TodoRepository) : ViewModel() {

    // Sửa thành nullable LiveData
    private val _registrationStatus = MutableLiveData<Boolean?>()
    val registrationStatus: LiveData<Boolean?> = _registrationStatus

    private val _loginStatus = MutableLiveData<User?>()
    val loginStatus: LiveData<User?> = _loginStatus

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun register(username: String, email: String, password: String) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Please fill all fields"
            return
        }

        viewModelScope.launch {
            try {
                // Check if user already exists
                val existingUser = repository.getUserByEmail(email)
                if (existingUser != null) {
                    _errorMessage.postValue("Email already registered")
                    _registrationStatus.postValue(false)
                    return@launch
                }

                val user = User(
                    username = username,
                    email = email,
                    password = password
                )
                val userId = repository.register(user)
                _registrationStatus.postValue(userId > 0)
            } catch (e: Exception) {
                _errorMessage.postValue("Registration failed: ${e.message}")
                _registrationStatus.postValue(false)
            }
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Please fill all fields"
            return
        }

        viewModelScope.launch {
            try {
                val user = repository.login(email, password)
                _loginStatus.postValue(user)
                if (user == null) {
                    _errorMessage.postValue("Invalid email or password")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Login failed: ${e.message}")
                _loginStatus.postValue(null)
            }
        }
    }

    fun clearStatus() {
        _registrationStatus.value = null
        _loginStatus.value = null
        _errorMessage.value = null
    }
}