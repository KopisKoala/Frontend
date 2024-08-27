package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("reviewCount")
    val reviewCount: Int,
    @SerialName("reviewList")
    val reviewList: List<ReviewX>
)