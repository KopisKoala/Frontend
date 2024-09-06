package com.example.whashow.api

import com.example.whashow.data.PairReview
import com.example.whashow.data.PerformanceReview
import com.example.whashow.data.ReviewLike
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewService {

    @GET("/review/list/performance")
    fun getReview(
        @Header("Authorization") accessToken: String,
        @Query("performanceId") performanceId: Int,
        @Query("way") sortWay: String
    ): Call<PerformanceReview>

    @GET("/review/list/pair")
    fun getInfo(
        @Header("Authorization") accessToken: String,
        @Query("pairId") id: Int,
        @Query("way") sortWay: String
    ): Call<PairReview>

    @POST("/review/{review-id}/like/create")
    fun postLike(
        @Header("Authorization") accessToken: String,
        @Path("review-id") id: Int
    ): Call<ReviewLike>

    @DELETE("/review/{review-id}/like/delete")
    fun deleteLike(
        @Header("Authorization") accessToken: String,
        @Path("review-id") id: Int
    ): Call<ReviewLike>
}