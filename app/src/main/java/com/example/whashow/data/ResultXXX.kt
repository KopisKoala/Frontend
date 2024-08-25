package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultXXX(
    @SerialName("favoriteActorCount")
    val favoriteActorCount: Int,
    @SerialName("favoriteActorResDtoList")
    val favoriteActorResDtoList: List<FavoriteActorResDto>
)