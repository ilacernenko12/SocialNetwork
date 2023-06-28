package com.example.socialnetwork.utils

import android.content.Context
import android.provider.Settings.Global.getString
import androidx.appcompat.app.AppCompatActivity
import com.example.socialnetwork.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class LocaleHelper {
    companion object {
        fun checkLocale(context: Context) {
            val sharedPreferences = context.getSharedPreferences("language", AppCompatActivity.MODE_PRIVATE)
            val languageCode = sharedPreferences.getString("selected_language", "en")
            val locale: Locale = if (languageCode != null) {
                Locale(languageCode)
            } else {
                Locale("en")
            }
            Locale.setDefault(locale)

            val resources = context.resources
            val configuration = resources.configuration
            configuration.setLocale(locale)

            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
    }
}