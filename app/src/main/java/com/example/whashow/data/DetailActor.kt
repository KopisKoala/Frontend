package com.example.whashow.data

import com.google.gson.annotations.SerializedName

class DetailActor(
    @SerializedName("id") val id: Int,
    @SerializedName("actorName") val actorName: String,
    @SerializedName("actorProfile") val actorProfile: String,
    @SerializedName("isFavoriteActor") var isFavoriteActor: String,
    @SerializedName("characterName") val characterName: String
)