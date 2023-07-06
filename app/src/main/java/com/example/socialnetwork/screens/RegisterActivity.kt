package com.example.socialnetwork.screens

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.socialnetwork.R
import com.example.socialnetwork.databinding.ActivityRegisterBinding
import com.example.socialnetwork.utils.LocaleHelper
import com.example.socialnetwork.utils.ViewUtils
import com.example.socialnetwork.viewmodels.RegistrationViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class RegisterActivity : AppCompatActivity(R.layout.activity_register) {

    private val binding: ActivityRegisterBinding by viewBinding(ActivityRegisterBinding::bind)
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isUserRegistered()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        observeRegistrationStatus()
        setOnClickListener()
        LocaleHelper.checkLocale(this)
    }

    private fun setOnClickListener() {
        binding.apply {
            btnRegister.setOnClickListener {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                val email = etEmail.text.toString()

                viewModel.registerUser(username, email, password)
            }
        }
    }

    private fun observeRegistrationStatus() {
        // Наблюдение за изменениями статуса регистрации
        viewModel.registrationStatus.observe(this) { isSuccess ->
            if (isSuccess) {
                // Регистрация успешна
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                setUserRegistered()
                finish()
            } else {
                // Регистрация не удалась
                binding.apply {
                    ViewUtils.clearField(etUsername)
                    ViewUtils.clearField(etPassword)
                    ViewUtils.clearField(etEmail)
                }
            }
        }
    }

    private fun isUserRegistered(): Boolean {
        val sharedPref = getSharedPreferences("registration", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("isRegistered", false)
    }

    private fun setUserRegistered() {
        val sharedPref = getSharedPreferences("registration", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isRegistered", true)
        editor.apply()
    }
}