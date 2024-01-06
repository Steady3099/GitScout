package com.example.gitscout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitscout.data.model.User
import com.example.gitscout.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _userProfile = MutableLiveData<User>()
    val userProfile: LiveData<User>
        get() = _userProfile

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun fetchUserProfile(username: String) {
        _loading.value = true

        userRepository.getUserProfile(username, object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val userProfile = response.body()
                    _userProfile.value = userProfile
                }
                _loading.value = false
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                _loading.value = false
                // Handle failure or show error state
            }
        })
    }
}
