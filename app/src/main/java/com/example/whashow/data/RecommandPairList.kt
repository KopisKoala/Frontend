package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommandPairList(
    @SerialName("code")
    val code: String,
    @SerialName("isSuccess")
    val isSuccess: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: RecommandPairListDTO
)
@Serializable
data class RecommandPairListDTO(
    @SerialName("recommendPairResDtoList")
    val recommendPairResDtoList: List<RecommendPairResDto>
)

@Serializable
data class RecommendPairResDto(
    @SerialName("pairDetailResDtoByStandard")
    val pairDetailResDtoByStandard: PairDetailResDtoByStandard,
    @SerialName("standard")
    val standard: String
)

@Serializable
data class PairDetailResDtoByStandard(
    @SerialName("actor1Name")
    val actor1Name: String,
    @SerialName("actor1Profile")
    val actor1Profile: String,
    @SerialName("actor2Name")
    val actor2Name: String,
    @SerialName("actor2Profile")
    val actor2Profile: String,
    @SerialName("hashtag1")
    val hashtag1: String,
    @SerialName("hashtag2")
    val hashtag2: String,
    @SerialName("hashtag3")
    val hashtag3: String,
    @SerialName("id")
    val id: Int,
    @SerialName("ratingAverage")
    val ratingAverage: Int,
    @SerialName("reviewCount")
    val reviewCount: Int,
    @SerialName("title")
    val title: String
)