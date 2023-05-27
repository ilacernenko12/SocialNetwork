package com.example.socialnetwork.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.socialnetwork.SocialNetworkApp
import com.example.socialnetwork.database.entity.UserEntity
import com.example.socialnetwork.database.repository.impl.UserRepositoryImpl
import kotlinx.coroutines.launch

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepositoryImpl = UserRepositoryImpl(SocialNetworkApp.room.userDao)

    private val _registrationStatus = MutableLiveData<Boolean>()
    val registrationStatus: LiveData<Boolean> = _registrationStatus

    fun registerUser(username: String, email: String, password: String) {
        val isValid = isUsernameValid(username) && isEmailValid(email) && isPasswordValid(password)

        if (isValid) {
            // Выполнение регистрации пользователя
            val userEntity = UserEntity(
                id = 0,
                username = username,
                email = email,
                password = password
            )
            viewModelScope.launch {
                repository.registerUser(userEntity)
            }
            _registrationStatus.value = true
        } else {
            _registrationStatus.value = false
        }
    }

    private fun isUsernameValid(username: String): Boolean {
        return username.isNotEmpty()
    }

    private fun isEmailValid(email: String): Boolean {
        val pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        return email.matches(pattern)
    }

    private fun isPasswordValid(password: String): Boolean {
        val pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{8,}\$".toRegex()
        return password.matches(pattern)
    }
}