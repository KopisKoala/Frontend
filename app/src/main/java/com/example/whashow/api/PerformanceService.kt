package com.example.whashow.api

import com.example.whashow.data.LikeResponse
import com.example.whashow.data.PerformanceAdvertiseResponse
import com.example.whashow.data.PerformanceDetailResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PerformanceService {
    @GET("/performance/advertise")
    fun getAdvertise(
    @Header("Authorization") accessToken: String)
    : Call<PerformanceAdvertiseResponse>

    @GET("/performance/popular/musicals")
    fun getPopularMusicals(
    @Header("Authorization") accessToken: String)
    : Call<PerformanceAdvertiseResponse>

    @GET("/performance/attract")
    fun getRecommend(
    @Header("Authorization") accessToken: String)
    : Call<PerformanceAdvertiseResponse>

    @GET("/performance/popular/plays")
    fun getPopularDrama(
    @Header("Authorization") accessToken: String)
    : Call<PerformanceAdvertiseResponse>

    @GET("/performance/detail/{performance-id}")
    fun getDetailInfo(
    @Header("Authorization") accessToken:String, @Path("performance-id") performanceId: Int
    ): Call<PerformanceDetailResponse>

    @POST("/favorite/actor/create/{actorId}")
    fun postLike(@Header("Authorization") accessToken:String, @Path("actorId") actorId:Int
    ) : Call<LikeResponse>

    @DELETE("/favorite/actor/delete/{actorId}")
    fun postDisLike(@Header("Authorization") accessToken:String, @Path("actorId") actorId:Int
    ) : Call<LikeResponse>





}