package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PairReview(
    @SerialName("code")
    val code: String,
    @SerialName("isSuccess")
    val isSuccess: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: PairReviewResult
)

@Serializable
data class PairReviewResult(
    @SerialName("averageRating")
    val averageRating: Float,
    @SerialName("hashtags")
    val hashtags: List<String>,
    @SerialName("reviewCount")
    val reviewCount: Int,
    @SerialName("reviewList")
    val reviewList: List<Review>
)
@Serializable
data class Review(
    @SerialName("content")
    val content: String,
    @SerialName("hashTag")
    val hashTag: String,
    @SerialName("id")
    val id: Int,
    @SerialName("isWriter")
    val isWriter: Boolean,
    @SerialName("likeCount")
    val likeCount: Int,
    @SerialName("rating")
    val rating: Int,
    @SerialName("writer")
    val writer: String,
    @SerialName("writerProfileImage")
    val writerProfileImage: String
)