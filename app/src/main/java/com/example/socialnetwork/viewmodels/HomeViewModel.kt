package com.example.socialnetwork.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socialnetwork.SocialNetworkApp
import com.example.socialnetwork.database.entity.AvatarModel
import com.example.socialnetwork.database.entity.PhotoModel
import com.example.socialnetwork.database.repository.impl.AvatarRepositoryImpl
import com.example.socialnetwork.database.repository.impl.PhotoRepositoryImpl
import com.example.socialnetwork.database.repository.impl.UserRepositoryImpl
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepositoryImpl = UserRepositoryImpl(SocialNetworkApp.room.userDao)
    private val avatarRepository: AvatarRepositoryImpl = AvatarRepositoryImpl(SocialNetworkApp.room.avatarDao)
    private val photosRepository: PhotoRepositoryImpl = PhotoRepositoryImpl((SocialNetworkApp.room.photoDao))

    private var _username = MutableLiveData<String>()
    val username: LiveData<String?> = _username

    private var _avatar = MutableLiveData<String>()
    val avatar: LiveData<String?> = _avatar

    private var _photos = MutableLiveData<List<PhotoModel>>()
    val photos: LiveData<List<PhotoModel>> = _photos

    init {
        loadUsername()
        loadAvatar()
        loadPhotos()
    }

    private fun loadAvatar() {
        viewModelScope.launch {
            avatarRepository.getAvatar().observeForever { avatar ->
                _avatar.value = avatar
            }
        }
    }

    private fun loadUsername() {
        viewModelScope.launch {
            userRepository.getUsername().observeForever { username ->
                _username.value = username
            }
        }
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            photosRepository.getAllPhotos().observeForever { photos ->
                _photos.value = photos
            }
        }
    }

    // Для добавления фото в раздел профиля
    fun insertPhoto(photo: PhotoModel) {
        viewModelScope.launch {
            photosRepository.insertPhoto(photo)
        }
    }

    // Для сохранения аватарки
    fun saveAvatar(image: AvatarModel) {
        viewModelScope.launch {
            avatarRepository.saveAvatar(image)
        }
    }
}