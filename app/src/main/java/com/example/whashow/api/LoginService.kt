package com.example.whashow.api

import com.example.whashow.data.KakaoLogin
import com.example.whashow.data.KakaoLoginRequest
import com.example.whashow.data.getNickname
import com.example.whashow.data.getNicknameRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.math.BigInteger

interface LoginService {
    @POST("/token/generate")
    fun login(@Body requestbody: KakaoLoginRequest): Call<KakaoLogin>
    @POST("/users/nickname")
    fun getNickname(@Header("Authorization") accessToken:String, @Body requestbody: getNicknameRequest): Call<getNickname>
}