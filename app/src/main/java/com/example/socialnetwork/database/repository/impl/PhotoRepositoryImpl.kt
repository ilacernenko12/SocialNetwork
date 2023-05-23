package com.example.socialnetwork.database.repository.impl

import com.example.socialnetwork.database.dao.PhotoDao
import com.example.socialnetwork.database.entity.PhotoModel
import com.example.socialnetwork.database.repository.PhotoRepository

class PhotoRepositoryImpl(private val photoDao: PhotoDao): PhotoRepository {
    override fun getAllPhotos() = photoDao.getAllPhotos()
    override suspend fun insertPhoto(photo: PhotoModel) = photoDao.insertPhoto(photo)
}