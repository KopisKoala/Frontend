package com.example.whashow.api

import com.example.whashow.data.RecommandPairList
import com.example.whashow.data.RecommandPerformanceList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecommandService {
    @GET("/performance/recommend")
    fun getPerformanceList(
        @Header("Authorization") accessToken: String,
        @Query("type") type:Int,
        @Query("startYear") year:Int,
        @Query("startMonth") startMonth:Int,
        @Query("startDate") startDate:Int,
        @Query("endYear") endYear:Int,
        @Query("endMonth") endMonth:Int,
        @Query("endDate") endDate:Int,
        @Query("location") location:String,
        @Query("minPrice") minPrice:Int,
        @Query("maxPrice") maxPrice:Int,
    ) : Call<RecommandPerformanceList>

    @GET("/pair/recommend")
    fun getPairList(
        @Header("Authorization") accessToken: String,
    ): Call<RecommandPairList>
}