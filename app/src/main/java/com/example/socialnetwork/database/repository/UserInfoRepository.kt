package com.example.socialnetwork.database.repository

import androidx.lifecycle.LiveData
import com.example.socialnetwork.database.entity.UserInfoModel

interface UserInfoRepository {
    suspend fun addUserInfo(userInfoModel: UserInfoModel)
    suspend fun getUserInfo(): UserInfoModel
}