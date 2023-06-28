package com.example.socialnetwork.screens

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.viewModels
import com.example.socialnetwork.databinding.FragmentSettingBinding
import com.example.socialnetwork.viewmodels.SettingViewModel
import java.util.*

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private val viewModel: SettingViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("language", MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSwitchRussian.setOnClickListener {
                changeLanguage("ru")
            }
            btnSwitchEnglish.setOnClickListener {
                changeLanguage("en")
            }
        }
    }

    private fun changeLanguage(languageCode: String) {
        val context = requireContext().applicationContext
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        saveLanguageToPrefs(languageCode, sharedPreferences)
        requireActivity().recreate()
    }

    private fun saveLanguageToPrefs(language: String, sharedPreferences: SharedPreferences) {
        sharedPreferences.edit()
            .putString("selected_language", language)
            .apply()
    }
}