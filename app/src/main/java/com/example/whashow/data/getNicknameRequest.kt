package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class getNicknameRequest(
    @SerialName("nickname")
    val nickname: String
)