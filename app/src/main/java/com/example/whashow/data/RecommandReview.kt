package com.example.whashow.data

import kotlinx.serialization.SerialName

data class RecommandReview(
    @SerialName("averageRating")
    val averageRating: Float,
    @SerialName("reviewCount")
    val reviewCount: Int,
    @SerialName("reviewList")
    val reviewList: List<Review>
)
