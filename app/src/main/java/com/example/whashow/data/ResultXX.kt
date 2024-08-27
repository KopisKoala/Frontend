package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultXX(
    @SerialName("goodsCount")
    val goodsCount: Int,
    @SerialName("goodsList")
    val goodsList: List<GoodsX>
)