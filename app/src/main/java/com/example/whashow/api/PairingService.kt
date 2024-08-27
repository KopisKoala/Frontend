package com.example.whashow.api

import com.example.whashow.data.Info
import com.example.whashow.data.PairReview
import com.example.whashow.data.ReviewLike
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PairingService {
    @GET("/review/list/pair")
    fun getInfo(@Header("Authorization") accessToken:String,@Query("pairId") id:Int,@Query("way") sortWay:String): Call<PairReview>
    @POST("/review/{review-id}/like/create")
    fun postLike(@Header("Authorization") accessToken:String,@Path("review-id")id:Int): Call<ReviewLike>
    @DELETE("/review/{review-id}/like/delete")
    fun deleteLike(@Header("Authorization") accessToken:String,@Path("review-id")id:Int): Call<ReviewLike>
}