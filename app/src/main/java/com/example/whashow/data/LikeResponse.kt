package com.example.whashow.data

data class LikeResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Int
)