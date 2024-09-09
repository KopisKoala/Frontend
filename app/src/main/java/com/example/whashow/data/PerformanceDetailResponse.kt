package com.example.whashow.data

import com.example.whashow.data.responseBody.ActorListResponse
import com.google.gson.annotations.SerializedName

data class PerformanceDetailResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: PerformanceDetailResult
)

data class PerformanceDetailResult(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster") val poster: String,
    @SerializedName("hashtag1") val hashtag1: String,
    @SerializedName("hashtag2") val hashtag2: String,
    @SerializedName("hashtag3") val hashtag3: String,
    @SerializedName("ratingAverage") val ratingAverage: Float,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("hallName") val hallName: String,
    @SerializedName("streetAddress") val streetAddress: String,
    @SerializedName("runtime") val runtime: String,
    @SerializedName("state") val state: String,
    @SerializedName("ticketingLink") val ticketingLink: String,
    @SerializedName("isRestaurant") val isRestaurant: String,
    @SerializedName("isStore") val isStore: String,
    @SerializedName("isCafe") val isCafe: String,
    @SerializedName("isNolibang") val isNolibang: String,
    @SerializedName("isSuyu") val isSuyu: String,
    @SerializedName("isParking") val isParking: String,
    @SerializedName("performanceDetailActorListResDto") val performanceDetailActorListResDto: ActorListResponse
)