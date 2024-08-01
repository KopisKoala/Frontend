package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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