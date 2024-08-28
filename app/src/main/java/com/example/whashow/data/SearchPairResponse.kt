package com.example.whashow.data

import com.google.gson.annotations.SerializedName

data class SearchPairResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: PairResult
)

data class PairResult(
    @SerializedName("pairDetailListResDtos") val pairDetailListResDtos: List<PairDetailListResDto>,
    @SerializedName("totalPairCount") val totalPairCount: Int
)

data class PairDetailListResDto(
    @SerializedName("pairDetailResDtoList") val pairDetailResDtoList: List<PairDetailResDto>,
)

data class PairDetailResDto(
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
