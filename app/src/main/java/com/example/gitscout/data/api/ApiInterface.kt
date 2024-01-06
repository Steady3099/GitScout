package com.example.gitscout.data.api

import com.example.gitscout.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Call<User>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/following")
    fun getUserFollowings(@Path("username") username: String): Call<List<User>>
}