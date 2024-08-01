package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultXXX(
    @SerialName("averageRating")
    val averageRating: Int,
    @SerialName("hashtags")
    val hashtags: List<String>,
    @SerialName("reviewCount")
    val reviewCount: Int,
    @SerialName("reviewList")
    val reviewList: List<Review>
)