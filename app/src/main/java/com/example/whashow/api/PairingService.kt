package com.example.whashow.api

import com.example.whashow.data.PairReview
import com.example.whashow.data.PopularPairRankResponse
import com.example.whashow.data.RecommandPairList
import com.example.whashow.data.ReviewLike
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PairingService {

    @GET("/pair/popular/list")
    fun getPopularPair(
        @Header("Authorization") accessToken: String,
        @Query("size") size: Int
    ): Call<PopularPairRankResponse>

    @GET("/pair/recommend")
    fun getPairList(
        @Header("Authorization") accessToken: String,
        @Query("performanceId") performanceId:Int
    ): Call<RecommandPairList>

}