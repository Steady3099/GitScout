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
    private val _userFollowers = MutableLiveData<List<User>>()
    private val _userFollowings = MutableLiveData<List<User>>()
    val userProfile: LiveData<User>
        get() = _userProfile

    val userFollowers: LiveData<List<User>>
        get() = _userFollowers

    val userFollowings: LiveData<List<User>>
        get() = _userFollowings

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
                }else{
                    _userProfile.value = null
                }
                _loading.value = false
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                _loading.value = false
            }
        })
    }

    fun fetchUserFollowers(username: String) {
        _loading.value = true

        userRepository.getUserFollowers(username, object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val userProfile = response.body()
                    _userFollowers.value = userProfile
                }
                _loading.value = false
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _loading.value = false
            }
        })
    }

    fun fetchUserFollowings(username: String) {
        _loading.value = true

        userRepository.getUserFollowings(username, object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val userProfile = response.body()
                    _userFollowings.value = userProfile
                }
                _loading.value = false
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _loading.value = false
                // Handle failure or show error state
            }
        })
    }

}
