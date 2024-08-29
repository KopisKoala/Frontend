package com.example.whashow.data

data class ReviewRequest(
    val performanceId: Int,
    val performanceRating: Int,
    val pairId: Int,
    val pairRating: Int,
    val hashTag: String,
    val content: String,
    val performanceDate: String
)
