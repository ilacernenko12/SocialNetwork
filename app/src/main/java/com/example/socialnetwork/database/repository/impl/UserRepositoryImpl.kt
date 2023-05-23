package com.example.socialnetwork.database.repository.impl

import androidx.lifecycle.LiveData
import com.example.socialnetwork.database.dao.UserDao
import com.example.socialnetwork.database.entity.UserEntity
import com.example.socialnetwork.database.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override suspend fun registerUser(userEntity: UserEntity) = userDao.insertUser(userEntity)
    override suspend fun getUsername(): LiveData<String?> = userDao.getUsername()
}