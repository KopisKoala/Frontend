package com.example.whashow.api

import com.example.whashow.data.AddProfile
import com.example.whashow.data.CalendarDayReview
import com.example.whashow.data.CalendarMonth
import com.example.whashow.data.Info
import com.example.whashow.data.KakaoLogin
import com.example.whashow.data.KakaoLoginRequest
import com.example.whashow.data.Partner
import com.example.whashow.data.changeProfileRequestBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface MypageService {
    @Multipart
    @POST("/users/profile-image")
    fun changeProfile(@Header("Authorization") accessToken:String,@Part image:MultipartBody.Part): Call<AddProfile>
    @GET("/users/info")
    fun getInfo(@Header("Authorization") accessToken:String): Call<Info>
    @GET("/review/myPage/reviews")
    fun getMonthReview(@Header("Authorization") accessToken:String, @Query("month") month: String): Call<CalendarMonth>
    @GET("/review/myPage/review/{review-id}")
    fun getDayReview(@Header("Authorization") accessToken:String, @Path("review-id") reviewId: String): Call<CalendarDayReview>
    @GET("/review/myPage/viewingPartner/update/{review-id}")
    fun getPartner(@Header("Authorization") accessToken:String, @Path("review-id") reviewId: String, @Query("partnerNumber") partner: Int): Call<Partner>


}