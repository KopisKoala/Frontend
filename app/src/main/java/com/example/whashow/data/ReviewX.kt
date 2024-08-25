package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewX(
    @SerialName("id")
    val id: Int,
    @SerialName("performanceDate")
    val performanceDate: String,
    @SerialName("poster")
    val poster: String
)