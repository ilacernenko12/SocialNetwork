package com.example.socialnetwork.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.socialnetwork.SocialNetworkApp
import com.example.socialnetwork.database.entity.UserEntity
import com.example.socialnetwork.database.repository.impl.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepositoryImpl = UserRepositoryImpl(SocialNetworkApp.room.userDao)
    private val database = FirebaseDatabase.getInstance()
    private val authInstance = FirebaseAuth.getInstance()
    private val _registrationStatus = MutableLiveData<Boolean>()
    val registrationStatus: LiveData<Boolean> = _registrationStatus

    fun registerUser(username: String, email: String, password: String) {
        val isValid = isUsernameValid(username) && isEmailValid(email) && isPasswordValid(password)
        if (isValid) {
            authInstance.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        it.result.user?.uid?.let { uid ->
                            UserEntity(
                                id = 0,
                                username = username,
                                email = email,
                                password = password,
                                uid = uid
                            )
                        }?.let { user -> saveUserToRealtimeDatabase(user) }
                    }
                    return@addOnCompleteListener
                }

            // Приходится дублировать, т.к. корутина не работает в коллбэке
            val userEntity = UserEntity(
                id = 0,
                username = username,
                email = email,
                password = password,
                uid = ""
            )

            viewModelScope.launch {
                repository.registerUser(userEntity)
            }
            _registrationStatus.value = true
        } else {
            _registrationStatus.value = false
        }
    }

    private fun saveUserToRealtimeDatabase(user: UserEntity) {
        val ref = database.getReference("/users/${user.uid}")
        ref.setValue(user)
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