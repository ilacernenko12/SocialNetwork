package com.example.socialnetwork.database.repository.impl

import androidx.lifecycle.LiveData
import com.example.socialnetwork.database.dao.PhotoDao
import com.example.socialnetwork.database.dao.UserInfoDao
import com.example.socialnetwork.database.entity.UserInfoModel
import com.example.socialnetwork.database.repository.UserInfoRepository

class UserInfoRepositoryImpl(private val userInfoDao: UserInfoDao): UserInfoRepository {
    override suspend fun addUserInfo(userInfoModel: UserInfoModel) = userInfoDao.addUserInfo(userInfoModel)
    override suspend fun getUserInfo(): UserInfoModel = userInfoDao.getUserInfo()
}