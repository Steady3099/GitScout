package com.example.gitscout.data.repository

import com.example.gitscout.data.api.ApiInterface
import com.example.gitscout.data.model.User
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val userService: ApiInterface = retrofit.create(ApiInterface::class.java)

    fun getUserProfile(username: String, callback: Callback<User>) {
        userService.getUser(username).enqueue(callback)
    }

    fun getUserFollowers(username: String, callback: Callback<List<User>>) {
        userService.getUserFollowers(username).enqueue(callback)
    }

    fun getUserFollowings(username: String, callback: Callback<List<User>>) {
        userService.getUserFollowings(username).enqueue(callback)
    }
}