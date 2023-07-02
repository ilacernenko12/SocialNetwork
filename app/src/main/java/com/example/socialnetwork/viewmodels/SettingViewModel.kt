package com.example.socialnetwork.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.util.*

class SettingViewModel(application: Application): AndroidViewModel(application) {

    private var _currentLanguage = MutableLiveData<String>()
    var currentLanguage = _currentLanguage

    fun changeLanguage(language: String, context: Context) {
        _currentLanguage.value = language

        val context = context.applicationContext
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
    fun saveSwitchState(switchId: String, isChecked: Boolean, context: Context) {
        val sharedPreferences = context.getSharedPreferences("switch_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(switchId, isChecked)
        editor.apply()
    }

    fun restoreSwitchState(switchId: String, context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("switch_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(switchId, false)
    }

    fun saveLanguageToPrefs(language: String, sharedPreferences: SharedPreferences) {
        sharedPreferences.edit()
            .putString("selected_language", language)
            .apply()
    }
}