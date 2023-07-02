package com.example.socialnetwork.utils

import android.content.Context
import com.example.socialnetwork.R

class ThemeManager(private val context: Context) {

    fun setLightTheme() {
        context.setTheme(R.style.Theme_SocialNetwork)
    }

    fun setDarkTheme() {
        context.setTheme(R.style.Theme_SocialNetwork_Dark)
    }
}