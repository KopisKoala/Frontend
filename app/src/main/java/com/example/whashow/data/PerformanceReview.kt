package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PerformanceReview(
    @SerialName("code")
    val code: String,
    @SerialName("isSuccess")
    val isSuccess: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: PerformanceResultDTO
)

@Serializable
data class PerformanceResultDTO(
    @SerialName("averageRating")
    val averageRating: Float,
    @SerialName("hashtags")
    val hashtags: List<String>,
    @SerialName("reviewCount")
    val reviewCount: Int,
    @SerialName("reviewList")
    val reviewList: List<PerformanceResultDTOList>,
    @SerialName("reviewSummary")
    val reviewSummary: String
)

@Serializable
data class PerformanceResultDTOList(
    @SerialName("content")
    val content: String,
    @SerialName("hashTag")
    val hashTag: String,
    @SerialName("id")
    val id: Int,
    @SerialName("isPressed")
    val isPressed: Boolean,
    @SerialName("isWriter")
    val isWriter: Boolean,
    @SerialName("likeCount")
    val likeCount: Int,
    @SerialName("rating")
    val rating: Int,
    @SerialName("writer")
    val writer: String,
    @SerialName("writerProfileImage")
    val writerProfileImage: String,
    @SerialName("writerRank")
    val writerRank: String
)