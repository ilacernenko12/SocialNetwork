package com.example.socialnetwork.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.socialnetwork.database.dao.AvatarDao
import com.example.socialnetwork.database.dao.PhotoDao
import com.example.socialnetwork.database.dao.UserDao
import com.example.socialnetwork.database.entity.AvatarModel
import com.example.socialnetwork.database.entity.PhotoModel
import com.example.socialnetwork.database.entity.UserEntity

@Database(entities = [UserEntity::class, AvatarModel::class, PhotoModel::class], version = 1, exportSchema = false)
abstract class RoomDb : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val avatarDao: AvatarDao
    abstract val photoDao: PhotoDao
}