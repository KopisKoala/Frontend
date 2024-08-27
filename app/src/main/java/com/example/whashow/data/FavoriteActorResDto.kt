package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteActorResDto(
    @SerialName("actorId")
    val actorId: Int,
    @SerialName("actorName")
    val actorName: String,
    @SerialName("actorProfile")
    val actorProfile: String,
    @SerialName("id")
    val id: Int
)