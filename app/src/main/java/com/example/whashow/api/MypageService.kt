package com.example.whashow.api

import com.example.whashow.data.AddMemo
import com.example.whashow.data.AddProfile
import com.example.whashow.data.CalendarDayReview
import com.example.whashow.data.CalendarMonth
import com.example.whashow.data.DeleteMyActor
import com.example.whashow.data.Goods
import com.example.whashow.data.Info
import com.example.whashow.data.MyActor
import com.example.whashow.data.PairDropdownResponse
import com.example.whashow.data.Partner
import com.example.whashow.data.ReviewRequest
import com.example.whashow.data.ReviewResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
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
    @PATCH("/review/myPage/viewingPartner/update/{review-id}")
    fun getPartner(@Header("Authorization") accessToken:String, @Path("review-id") reviewId: String, @Query("partnerNumber") partner: Int): Call<Partner>
    @PATCH("/review/myPage/memo/update/{review-id}")
    fun addReviewMemo(@Header("Authorization") accessToken:String, @Path("review-id") reviewId: String,@Query("memo") memo: String): Call<AddMemo>
    @GET("/goods/goods/list/all")
    fun getGoodsList(@Header("Authorization") accessToken:String): Call<Goods>
    @GET("/favorite/actor/list")
    fun getMyActorList(@Header("Authorization") accessToken:String,@Query("ScrollPosition") position: Int,@Query("FetchSize") size: Int): Call<MyActor>
    @DELETE("/favorite/actor/delete/{actorId}")
    fun deleteActor(@Header("Authorization") accessToken:String,@Path("actorId") id: Int): Call<DeleteMyActor>
    @GET("/pair/{performance-id}/pairs")
    fun getPairDropDown(@Header("Authorization") accessToken:String,@Path("performance-id") performanceId:Int): Call<PairDropdownResponse>
    @POST("/review/create")
    fun createReview(@Header("Authorization") accessToken:String,@Body reviewRequest : ReviewRequest): Call<ReviewResponse>
}