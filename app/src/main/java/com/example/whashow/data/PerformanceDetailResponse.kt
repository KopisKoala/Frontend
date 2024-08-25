package com.example.whashow.data

import com.google.gson.annotations.SerializedName

data class PerformanceDetailResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: PerformanceDetailResult
)