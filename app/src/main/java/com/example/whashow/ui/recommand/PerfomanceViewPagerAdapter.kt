package com.example.whashow.ui.recommand

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.data.PairReviewResult
import com.example.whashow.data.RecommandReview
import com.example.whashow.databinding.ListGridRecommandItemBinding
import com.example.whashow.databinding.PairResultVpItemBinding
import com.example.whashow.databinding.PerformanceReaultVpItemBinding
import com.example.whashow.ui.pairing.PairingViewPagerAdapter

class PerformanceViewPagerAdapter (var list: ArrayList<RecommandReview>): RecyclerView.Adapter<PerformanceViewPagerAdapter.RecommandViewHolder>() {

    var performanceList: ArrayList<RecommandReview> =list
        set(value){
            field=value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) : PerformanceViewPagerAdapter.RecommandViewHolder{
        return RecommandViewHolder(
            PerformanceReaultVpItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    inner class RecommandViewHolder(val binding: PerformanceReaultVpItemBinding) : RecyclerView.ViewHolder(binding.root){
        val sort=binding.sort
        val rating=binding.ratingBar
    }

    override fun onBindViewHolder(holder: PerformanceViewPagerAdapter.RecommandViewHolder, position: Int) {
        holder.rating.rating=performanceList[position].averageRating

    }

}