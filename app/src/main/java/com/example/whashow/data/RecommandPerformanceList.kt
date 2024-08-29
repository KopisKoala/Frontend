package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommandPerformanceList(
    @SerialName("code")
    val code: String,
    @SerialName("isSuccess")
    val isSuccess: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: RecommandPerformanceListDTO
)

@Serializable
data class RecommandPerformanceListDTO(
    @SerialName("performancesByStandardList")
    val performancesByStandardList: List<PerformancesByStandard>
)

@Serializable
data class PerformancesByStandard(
    @SerialName("performancesByStandard")
    val performancesByStandard: List<PerformancesByStandardList>,
    @SerialName("standard")
    val standard: String
)

@Serializable
data class PerformancesByStandardList(
    @SerialName("endDate")
    val endDate: String,
    @SerialName("id")
    val id: Int,
    @SerialName("poster")
    val poster: String,
    @SerialName("price")
    val price: String,
    @SerialName("ratingAverage")
    val ratingAverage: Float,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("title")
    val title: String
)