package com.example.whashow.data.responseBody

import com.example.whashow.data.DetailActor
import com.google.gson.annotations.SerializedName

data class ActorListResponse(
    @SerializedName("actorList") val actorList: List<DetailActor>
)
