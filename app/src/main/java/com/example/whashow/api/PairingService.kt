package com.example.whashow.api

import com.example.whashow.data.Info
import com.example.whashow.data.PairReview
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface PairingService {
    @GET("/review/review/list/pair")
    fun getInfo(@Header("Authorization") accessToken:String): Call<PairReview>

}