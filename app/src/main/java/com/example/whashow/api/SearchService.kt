package com.example.whashow.api

import SearchHomeResponse
import com.example.whashow.data.PopularPairRankResponse
import com.example.whashow.data.SearchPairResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchService {
    @GET("/search/home")
    fun getSearchHome(
        @Header("Authorization") accessToken: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Call<SearchHomeResponse>

    @GET("/search/pairs/performance")
    fun getSearchPair(
        @Header("Authorization") accessToken: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Call<SearchPairResponse>

    @GET("/pair/popular/list")
    fun getPopularPair(
        @Header("Authorization") accessToken: String,
        @Query("size") size: Int
    ): Call<PopularPairRankResponse>
}