package com.example.whashow.data

import com.google.gson.annotations.SerializedName

data class ActorListResponse(
    @SerializedName("actorList") val actorList: List<DetailActor>
)
