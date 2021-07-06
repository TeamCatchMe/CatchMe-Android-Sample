package com.teamcatchme.add_action_seojin

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServiceCreator {
    private const val BASE_URL = "http://52.14.194.163:5000/"
    private val gson = GsonConverterFactory.create()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(gson)
        .build()
    val postImgService: PostImg = retrofit.create(PostImg::class.java)
}