package com.example.whashow.data

import com.google.gson.annotations.SerializedName

data class PopularPairRankResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: PopularPairRankResult
)

data class PopularPairRankResult(
    @SerializedName("pairDetailResDtoList") val pairDetailResDtoList: List<PopularPairDetailResDto>
)

data class PopularPairDetailResDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("actor1Name") val actor1Name: String,
    @SerializedName("actor2Name") val actor2Name: String,
    @SerializedName("actor1Profile") val actor1Profile: String,
    @SerializedName("actor2Profile") val actor2Profile: String,
    @SerializedName("hashtag1") val hashtag1: String?,
    @SerializedName("hashtag2") val hashtag2: String?,
    @SerializedName("hashtag3") val hashtag3: String?,
    @SerializedName("reviewCount") val reviewCount: Int,
    @SerializedName("ratingAverage") val ratingAverage: Double
)