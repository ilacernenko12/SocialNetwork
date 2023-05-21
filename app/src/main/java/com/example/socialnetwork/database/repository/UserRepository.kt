package com.example.socialnetwork.database.repository

import androidx.lifecycle.LiveData
import com.example.socialnetwork.database.entity.UserEntity

interface UserRepository {
    suspend fun registerUser(userEntity: UserEntity)
    suspend fun getUsername(): LiveData<String?>
}