package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CalendarDayReview(
    @SerialName("code")
    val code: String,
    @SerialName("isSuccess")
    val isSuccess: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: CalendarDayReviewDetail
)

@Serializable
data class CalendarDayReviewDetail(
    @SerialName("actor1Name")
    val actor1Name: String,
    @SerialName("actor2Name")
    val actor2Name: String,
    @SerialName("content")
    val content: String,
    @SerialName("hashtag")
    val hashtag: String,
    @SerialName("id")
    val id: Int,
    @SerialName("memo")
    val memo: String,
    @SerialName("pairRatings")
    val pairRatings: Int,
    @SerialName("performanceDate")
    val performanceDate: String,
    @SerialName("performanceName")
    val performanceName: String,
    @SerialName("performanceRatings")
    val performanceRatings: Int,
    @SerialName("performanceType")
    val performanceType: String,
    @SerialName("poster")
    val poster: String,
    @SerialName("viewingPartner")
    val viewingPartner: String
)