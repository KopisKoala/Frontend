package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyActor(
    @SerialName("code")
    val code: String,
    @SerialName("isSuccess")
    val isSuccess: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: MyActorResult
)

@Serializable
data class MyActorResult(
    @SerialName("favoriteActorCount")
    val favoriteActorCount: Int,
    @SerialName("favoriteActorResDtoList")
    val favoriteActorResDtoList: List<FavoriteActorResDto>
)

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