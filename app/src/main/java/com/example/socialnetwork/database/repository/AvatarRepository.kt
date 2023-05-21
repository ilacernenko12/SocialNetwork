package com.example.socialnetwork.database.repository

import androidx.lifecycle.LiveData
import com.example.socialnetwork.database.entity.AvatarModel
import com.example.socialnetwork.database.entity.UserEntity

interface AvatarRepository {
    suspend fun saveAvatar(avatarModel: AvatarModel)
    suspend fun getAvatar(): LiveData<String?>
}