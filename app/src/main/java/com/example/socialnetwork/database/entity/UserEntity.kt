package com.example.socialnetwork.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_database")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val email: String,
    val password: String,
    val uid: String
)