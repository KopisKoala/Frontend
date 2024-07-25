package com.example.whashow.ui.pairing

data class RecentPairing(
    val imgPair1:Int,
    val imgPair2:Int,
    val names:String,
    val performance:String,
    val hashtag: ArrayList<Hashtag>
)
