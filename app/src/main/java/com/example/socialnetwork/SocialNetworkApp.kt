package com.example.socialnetwork

import android.app.Application
import com.example.socialnetwork.database.RoomDb
import com.example.socialnetwork.utils.DbUtils
import com.example.socialnetwork.utils.ThemeManager

class SocialNetworkApp: Application() {
    lateinit var themeManager: ThemeManager
    companion object {
        lateinit var room: RoomDb
    }

    override fun onCreate() {
        super.onCreate()
        room = DbUtils.getRoom(this)
        themeManager = ThemeManager(this)
    }
}