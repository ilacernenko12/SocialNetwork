package com.example.socialnetwork.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "avatars")
data class AvatarModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val imagePath: String
)