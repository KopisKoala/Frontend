package com.example.whashow.ui.pairing.pairingAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.R
import com.example.whashow.data.PairReviewResult
import com.example.whashow.data.PerformancesByStandard
import com.example.whashow.data.RecommendPairResDto


import com.example.whashow.databinding.PairResultVpItemBinding

class PairingViewPagerAdapter (var list: ArrayList<RecommendPairResDto>) : RecyclerView.Adapter<PairingViewPagerAdapter.PagerViewHolder>() {

    var pairingList: ArrayList<RecommendPairResDto> =list
        set(value){
            field=value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) : PagerViewHolder {
        return PagerViewHolder(
            PairResultVpItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    inner class PagerViewHolder(val binding: PairResultVpItemBinding) : RecyclerView.ViewHolder(binding.root){
        val names=binding.names
        val sort=binding.sort
        val rating=binding.ratingBar
        val hashtag1=binding.hashtag1
        val hashtag2=binding.hashtag2
        val actor1Img=binding.paringImg1
        val actor2Img=binding.paringImg2
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.rating.rating=pairingList[position].pairDetailResDtoByStandard.ratingAverage.toFloat()
        holder.names.text=pairingList[position].pairDetailResDtoByStandard.actor1Name + pairingList[position].pairDetailResDtoByStandard.actor2Name
        holder.sort.text=pairingList[position].standard

        if (pairingList[position].pairDetailResDtoByStandard.hashtag1=="null"){
            holder.binding.hashtag1.visibility= View.GONE
            holder.binding.hashtag2.visibility=View.GONE
        }
        else if (pairingList[position].pairDetailResDtoByStandard.hashtag2=="null"){
            holder.binding.hashtag1.text="#"+pairingList[position].pairDetailResDtoByStandard.hashtag1
            holder.binding.hashtag2.visibility=View.GONE
        }
        else {
            holder.binding.hashtag1.text="#"+pairingList[position].pairDetailResDtoByStandard.hashtag1
            holder.binding.hashtag2.text="#"+pairingList[position].pairDetailResDtoByStandard.hashtag2
        }

        Glide.with(holder.itemView.context)
            .load(pairingList[position].pairDetailResDtoByStandard.actor1Profile)
            .override(1500, 1500)
            .placeholder(R.drawable.actor1_rectangle) // 이미지 로딩 중에 표시될 placeholder 이미지
            .error(R.drawable.actor1_rectangle) // 이미지 로딩 실패 시 표시될 이미지
            .into(holder.actor1Img)

        Glide.with(holder.itemView.context)
            .load(pairingList[position].pairDetailResDtoByStandard.actor2Profile)
            .override(1500, 1500)
            .placeholder(R.drawable.actor2_rectangle) // 이미지 로딩 중에 표시될 placeholder 이미지
            .error(R.drawable.actor2_rectangle) // 이미지 로딩 실패 시 표시될 이미지
            .into(holder.actor2Img)
    }

    fun updatePair(newList: List<RecommendPairResDto>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }



}