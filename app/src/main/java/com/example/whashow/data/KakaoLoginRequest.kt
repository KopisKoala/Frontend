package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginRequest(
    @SerialName("email")
    val email: String,
    @SerialName("provider")
    val provider: String,
    @SerialName("username")
    val username: String
)