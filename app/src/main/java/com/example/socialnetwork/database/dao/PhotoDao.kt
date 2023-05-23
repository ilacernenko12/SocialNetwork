package com.example.socialnetwork.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.socialnetwork.database.entity.PhotoModel

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhoto(photo: PhotoModel)

    @Query("SELECT * FROM photos")
    fun getAllPhotos(): LiveData<List<PhotoModel>>
}