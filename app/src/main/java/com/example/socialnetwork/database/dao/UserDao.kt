package com.example.socialnetwork.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.socialnetwork.database.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT username FROM user_database ORDER BY id DESC LIMIT 1")
    fun getUsername(): LiveData<String?>
}