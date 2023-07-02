package com.example.socialnetwork

import android.app.Application
import com.example.socialnetwork.database.RoomDb
import com.example.socialnetwork.utils.DbUtils

class SocialNetworkApp: Application() {
    companion object {
        lateinit var room: RoomDb
    }

    override fun onCreate() {
        super.onCreate()
        room = DbUtils.getRoom(this)
    }
}