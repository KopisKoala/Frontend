package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultX(
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