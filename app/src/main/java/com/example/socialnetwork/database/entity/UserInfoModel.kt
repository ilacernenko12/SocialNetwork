package com.example.socialnetwork.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfoModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val shortBio: String,
    val age: String,
    val city: String,
)
