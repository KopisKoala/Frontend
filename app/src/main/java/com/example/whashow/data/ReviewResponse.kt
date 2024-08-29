package com.example.whashow.data

data class ReviewResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Int
)
