package com.example.whashow.api


import SearchResponse
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
    ): Call<SearchResponse>
}