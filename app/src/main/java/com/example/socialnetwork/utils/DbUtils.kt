package com.example.socialnetwork.utils

import android.content.Context
import androidx.room.Room
import com.example.socialnetwork.database.RoomDb

class DbUtils {
    companion object {
        fun getRoom(context: Context): RoomDb {
            return Room.databaseBuilder(
                context,
                RoomDb::class.java,
                "app_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}