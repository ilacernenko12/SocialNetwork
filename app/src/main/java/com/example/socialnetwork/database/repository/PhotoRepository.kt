package com.example.socialnetwork.database.repository

import androidx.lifecycle.LiveData
import com.example.socialnetwork.database.entity.PhotoModel

interface PhotoRepository {
    fun getAllPhotos(): LiveData<List<PhotoModel>>
    suspend fun insertPhoto(photo: PhotoModel)
}