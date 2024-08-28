package com.example.whashow.ui.pairing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.data.PairReviewResult


import com.example.whashow.databinding.PairResultVpItemBinding

class PairingViewPagerAdapter (var list: ArrayList<PairReviewResult>) : RecyclerView.Adapter<PairingViewPagerAdapter.PagerViewHolder>() {

    var pairingList: ArrayList<PairReviewResult> =list
        set(value){
            field=value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) : PairingViewPagerAdapter.PagerViewHolder{
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
    }

    override fun onBindViewHolder(holder: PairingViewPagerAdapter.PagerViewHolder, position: Int) {
        holder.rating.rating=pairingList[position].averageRating
        if (pairingList[position].hashtags[0]=="null"){
            holder.binding.hashtag1.visibility= View.GONE
            holder.binding.hashtag2.visibility=View.GONE
        }
        else if (pairingList[position].hashtags[1]=="null"){
            holder.binding.hashtag1.text="#"+pairingList[position].hashtags[0]
            holder.binding.hashtag2.visibility=View.GONE
        }
        else {
            holder.binding.hashtag1.text="#"+pairingList[position].hashtags[0]
            holder.binding.hashtag2.text="#"+pairingList[position].hashtags[1]
        }
    }


}