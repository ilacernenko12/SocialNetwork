package com.example.socialnetwork.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.socialnetwork.database.entity.UserInfoModel

@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserInfo(userInfoModel: UserInfoModel)

    @Query("SELECT * FROM user_info ORDER BY id DESC LIMIT 1")
    suspend fun getUserInfo(): UserInfoModel
}