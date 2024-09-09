package com.example.whashow.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Goods(
    @SerialName("code")
    val code: String,
    @SerialName("isSuccess")
    val isSuccess: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: GoodsResult
)

@Serializable
data class GoodsResult(
    @SerialName("goodsCount")
    val goodsCount: Int,
    @SerialName("goodsList")
    val goodsList: List<GoodsResultDto>
)

@Serializable
data class GoodsResultDto(
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int,
    @SerialName("title")
    val title: String
)