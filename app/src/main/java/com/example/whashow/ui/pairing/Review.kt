package com.example.whashow.ui.pairing

import android.media.Rating

data class Review(
    val img: Int,
    val name:String,
    val rating: Float,
    val feature: String,
    val review:String,
    var check: Boolean,
    val likeNum:String
)
