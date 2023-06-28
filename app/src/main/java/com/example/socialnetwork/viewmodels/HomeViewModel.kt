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
import com.example.socialnetwork.database.repository.impl.UserInfoRepositoryImpl
import com.example.socialnetwork.database.repository.impl.UserRepositoryImpl
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepositoryImpl =
        UserRepositoryImpl(SocialNetworkApp.room.userDao)
    private val avatarRepository: AvatarRepositoryImpl =
        AvatarRepositoryImpl(SocialNetworkApp.room.avatarDao)
    private val photosRepository: PhotoRepositoryImpl =
        PhotoRepositoryImpl((SocialNetworkApp.room.photoDao))
    private val userInfoRepository: UserInfoRepositoryImpl =
        UserInfoRepositoryImpl((SocialNetworkApp.room.userInfoDao))

    private var _username = MutableLiveData<String>()
    val username: LiveData<String?> = _username

    private var _avatar = MutableLiveData<String>()
    val avatar: LiveData<String?> = _avatar

    private var _photos = MutableLiveData<List<PhotoModel>>()
    val photos: LiveData<List<PhotoModel>> = _photos

    private var _shortBio = MutableLiveData<String>()
    val shortBio: LiveData<String> = _shortBio

    private var _age = MutableLiveData<String>()
    val age: LiveData<String> = _age

    private var _city = MutableLiveData<String>()
    val city: LiveData<String> = _city

    init {
        loadUsername()
        loadAvatar()
        loadPhotos()
//        loadUserInfo()
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

    private fun loadUserInfo() {
        viewModelScope.launch {
            val userInfo =  userInfoRepository.getUserInfo()
            _shortBio.value = userInfo.shortBio
            _age.value = userInfo.age
            _city.value = userInfo.city
        }
    }
}