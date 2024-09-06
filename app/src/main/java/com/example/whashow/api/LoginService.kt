package com.example.whashow.api

import com.example.whashow.data.KakaoLogin
import com.example.whashow.data.requestBody.KakaoLoginRequest
import com.example.whashow.data.Reissue
import com.example.whashow.data.getNickname
import com.example.whashow.data.requestBody.getNicknameRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {
    @POST("/token/generate")
    fun login(@Body requestbody: KakaoLoginRequest): Call<KakaoLogin>
    @POST("/users/reissue")
    fun reissue(@Header("Authorization") refreshToken:String):Call<Reissue>
    @POST("/users/nickname")
    fun getNickname(@Header("Authorization") accessToken:String, @Body requestbody: getNicknameRequest): Call<getNickname>
}