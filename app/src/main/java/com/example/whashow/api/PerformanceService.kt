package com.example.whashow.api

import com.example.whashow.data.PerformanceDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PerformanceService {


    @GET("/performance/detail/{performance-id}")
    fun getDetailInfo(@Header("Authorization") accessToken:String, @Path("performance-id") performanceId: Int
    ): Call<PerformanceDetailResponse>
}