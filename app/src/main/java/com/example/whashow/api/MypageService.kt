package com.example.whashow.api

import com.example.whashow.data.AddProfile
import com.example.whashow.data.KakaoLogin
import com.example.whashow.data.KakaoLoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MypageService {
    @POST("/users/profile-image")
    fun changeProfile(@Header("Authorization") accessToken:String,imgUri:String): Call<AddProfile>
}