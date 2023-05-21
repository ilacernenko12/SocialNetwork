package com.example.socialnetwork.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.socialnetwork.R
import com.example.socialnetwork.database.entity.AvatarModel
import com.example.socialnetwork.databinding.ActivityHomeBinding
import com.example.socialnetwork.viewmodels.HomeViewModel

class HomeActivity : AppCompatActivity(R.layout.activity_home) {

    private val binding: ActivityHomeBinding by viewBinding(ActivityHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val REQUEST_IMAGE_GALLERY = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeUsername()
        observeAvatar()

        binding.ivUserAvatar.setOnClickListener {
            openGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data

            // Загрузка изображения с помощью Glide
            Glide.with(this)
                .load(selectedImageUri)
                .into(binding.ivUserAvatar)

            // Сохранение изображения в базе данных
            val imageModel = AvatarModel(imagePath = selectedImageUri.toString())
            viewModel.saveAvatar(imageModel)
        }
    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }


    private fun observeUsername() {
        viewModel.username.observe(this) { username ->
            if (username != null) {
                binding.etUsername.text = username
            } else {
                binding.etUsername.text = "user123"
            }
        }
    }

    private fun observeAvatar() {
        viewModel.avatar.observe(this) { avatar ->
            Glide.with(this)
                .load(avatar)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(binding.ivUserAvatar)
        }
    }
}