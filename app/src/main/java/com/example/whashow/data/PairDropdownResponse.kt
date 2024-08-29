package com.example.whashow.data


data class PairDropdownResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ResultData
)

data class ResultData(
    val pairList: List<PairData>
)

data class PairData(
    val pairId: Int,
    val actor1: String,
    val actor2: String
)
