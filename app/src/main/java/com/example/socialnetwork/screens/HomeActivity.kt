package com.example.socialnetwork.screens

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.socialnetwork.R
import com.example.socialnetwork.database.entity.AvatarModel
import com.example.socialnetwork.databinding.ActivityHomeBinding
import com.example.socialnetwork.databinding.ChangeAvatarDialogBinding
import com.example.socialnetwork.viewmodels.HomeViewModel
import java.io.File
import android.Manifest
import android.util.Log
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HomeActivity : AppCompatActivity(R.layout.activity_home) {

    private val binding: ActivityHomeBinding by viewBinding(ActivityHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var imageCapture: ImageCapture
    private val REQUEST_IMAGE_GALLERY = 1
    private val CAMERA_PERMISSION_REQUEST_CODE = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация CameraX и ImageCapture
        cameraExecutor = initCameraX()
        imageCapture = initImageCapture()

        observeUsername()
        observeAvatar()
        registerBottomNavigationBar()

        binding.apply {
            ivUserAvatar.setOnClickListener {
                showDialogChangeAvatar()
            }
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

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }


    private fun createTempImageFile(): File {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("IMG_", ".jpg", storageDir)
    }


    private fun showDialogChangeAvatar() {
        val dialogBinding = ChangeAvatarDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()
        dialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.dialog_shape
            )
        )
        dialog.show()
        dialogBinding.btnChooseGallery.setOnClickListener {
            openGallery()
            dialog.cancel()
        }
        dialogBinding.btnChooseCamera.setOnClickListener {
            binding.previewView.visibility = View.VISIBLE
            binding.captureButton.visibility = View.VISIBLE
            binding.bottomNavigationView.visibility = View.GONE
            binding.captureButton.setOnClickListener {
                takePhoto()
            }
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }
            dialog.cancel()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also { it.setSurfaceProvider(binding.previewView.surfaceProvider) }

            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.e("qwefqwefweq", "Error starting camera: ${e.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        // Создаем файл для сохранения изображения
        val photoFile = File(externalMediaDirs.firstOrNull(), "${System.currentTimeMillis()}.jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri ?: photoFile.toUri()
                    val imagePath = savedUri.toString()

                    // Сохраняем изображение в Room базе данных
                    viewModel.saveAvatar(AvatarModel(imagePath = imagePath))
                    binding.previewView.visibility = View.GONE
                    binding.captureButton.visibility = View.GONE
                    binding.bottomNavigationView.visibility = View.VISIBLE

                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("QWREQWF", "Error saving image: ${exception.message}")
                }
            })
    }

    private fun allPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun observeUsername() {
        viewModel.username.observe(this) { username ->
            if (username != null) {
                binding.tvUsername.text = username
            } else {
                binding.tvUsername.text = "user123"
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

    private fun initCameraX(): ExecutorService = Executors.newSingleThreadExecutor()
    private fun initImageCapture(): ImageCapture = ImageCapture.Builder().build()

    private fun registerBottomNavigationBar() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Обработка нажатия на элемент Home
                    startActivity(intent)
                    true
                }
                R.id.navigation_feed -> {
                    // Обработка нажатия на элемент Dashboard
                    true
                }
                R.id.navigation_chat -> {
                    // Обработка нажатия на элемент Notifications
                    true
                }
                R.id.navigation_setting -> {
                    // Обработка нажатия на элемент Notifications
                    true
                }
                R.id.navigation_about -> {
                    // Обработка нажатия на элемент Notifications
                    true
                }
                else -> false
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
