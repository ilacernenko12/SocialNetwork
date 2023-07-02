package com.example.socialnetwork.screens

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.socialnetwork.databinding.FragmentSettingBinding
import com.example.socialnetwork.viewmodels.SettingViewModel

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private val viewModel: SettingViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private val RUSSIAN = "ru"
    private val ENGLISH = "en"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("language", MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        checkSwitcherPosition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            languageSwitcher.setOnCheckedChangeListener{ _, isChecked ->
                viewModel.saveSwitchState("language_switcher",isChecked, requireContext())
                if (isChecked) {
                    viewModel.apply {
                        changeLanguage("ru", requireContext())
                        saveLanguageToPrefs(RUSSIAN, sharedPreferences)
                    }
                } else {
                    viewModel.apply {
                        changeLanguage("en", requireContext())
                        saveLanguageToPrefs(ENGLISH, sharedPreferences)
                    }
                }
            }
        }
    }

    private fun checkSwitcherPosition() {
        binding.languageSwitcher.isChecked = viewModel.restoreSwitchState("language_switcher", requireContext())
        binding.themeSwitcher.isChecked = viewModel.restoreSwitchState("", requireContext())
        binding.notificationSwitcher.isChecked = viewModel.restoreSwitchState("", requireContext())
        binding.soundSwitcher.isChecked = viewModel.restoreSwitchState("", requireContext())
    }
}