package com.example.whashow.data

data class PerformanceAdvertiseResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: PerformanceListResult
)

data class PerformanceListResult(
    val performanceList: List<Performance>
)

data class Performance(
    val perfId: Int,
    val title: String,
    val poster: String,
    val rank: Int,
    val hall: String,
    val duration: String
)