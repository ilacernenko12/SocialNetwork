package com.example.socialnetwork.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.socialnetwork.database.entity.AvatarModel

@Dao
interface AvatarDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAvatar(avatar: AvatarModel)

    @Query("SELECT imagePath FROM avatars ORDER BY id DESC LIMIT 1")
    fun getUsername(): LiveData<String?>
}