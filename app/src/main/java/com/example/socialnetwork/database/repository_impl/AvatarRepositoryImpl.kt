package com.example.socialnetwork.database.repository_impl

import androidx.lifecycle.LiveData
import com.example.socialnetwork.database.dao.AvatarDao
import com.example.socialnetwork.database.entity.AvatarModel
import com.example.socialnetwork.database.repository.AvatarRepository

class AvatarRepositoryImpl(private val avatarDao: AvatarDao) : AvatarRepository {
    override suspend fun saveAvatar(avatar: AvatarModel) = avatarDao.saveAvatar(avatar)
    override suspend fun getAvatar(): LiveData<String?> = avatarDao.getUsername()
}