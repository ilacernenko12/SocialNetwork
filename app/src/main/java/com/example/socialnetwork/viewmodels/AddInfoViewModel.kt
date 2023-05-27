package com.example.socialnetwork.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socialnetwork.SocialNetworkApp
import com.example.socialnetwork.database.entity.UserInfoModel
import com.example.socialnetwork.database.repository.impl.UserInfoRepositoryImpl
import kotlinx.coroutines.launch

class AddInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val userInfoRepository: UserInfoRepositoryImpl =
        UserInfoRepositoryImpl((SocialNetworkApp.room.userInfoDao))

    private val _isEmptyFields = MutableLiveData<Boolean>()
    val isEmptyFields: LiveData<Boolean> = _isEmptyFields

    fun addUserInfo(shortBio: String, age: String, city: String) {
        val isValid = shortBio.isNotEmpty() && age.isNotEmpty() && city.isNotEmpty()

        if (isValid) {
            val userInfoModel = UserInfoModel(
                id = 0,
                shortBio = shortBio,
                age = age,
                city = city
            )

            viewModelScope.launch {
                userInfoRepository.addUserInfo(userInfoModel)
            }

            _isEmptyFields.value = false
        } else {
            _isEmptyFields.value = true
        }
    }
}