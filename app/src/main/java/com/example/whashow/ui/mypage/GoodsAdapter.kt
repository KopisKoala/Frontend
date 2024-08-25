package com.example.whashow.ui.mypage

import android.annotation.SuppressLint
import android.graphics.Paint
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.R
import com.example.whashow.data.GoodsX
import com.example.whashow.databinding.GoodsGridItemBinding
import com.example.whashow.databinding.ListGridPairingBinding
import com.example.whashow.databinding.ListRecentPairingBinding
import com.example.whashow.ui.pairing.Actor
import com.example.whashow.ui.pairing.ActorAdapterGrid
import com.example.whashow.ui.pairing.RecentAdapter
import com.example.whashow.ui.pairing.RecentPairing

class GoodsAdapter (var list: ArrayList<GoodsX>): RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GoodsAdapter.GoodsViewHolder{
        return GoodsViewHolder(
            GoodsGridItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class GoodsViewHolder(val binding: GoodsGridItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        val title=binding.title
        val img=binding.poster
        val whashowPrice=binding.whashowPrice
        val price=binding.price
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GoodsAdapter.GoodsViewHolder, position: Int) {
        holder.price.paintFlags=holder.price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.title.text="["+list[position].title+"] "+list[position].name
        Glide.with(holder.img.context)
            .load(list[position].image)
            .override(1500,1500)
            .placeholder(R.drawable.img_goods1) // 이미지 로딩 중에 표시될 placeholder 이미지
            .error(R.drawable.img_goods1) // 이미지 로딩 실패 시 표시될 이미지
            .into(holder.img)
        holder.whashowPrice.text=list[position].price.toString()+"원"
    }

    override fun getItemCount(): Int {
        return list.size
    }
}