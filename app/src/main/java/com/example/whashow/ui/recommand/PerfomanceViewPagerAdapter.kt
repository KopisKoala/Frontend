package com.example.whashow.ui.recommand

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.R
import com.example.whashow.data.PerformancesByStandard
import com.example.whashow.data.PerformancesByStandardList
import com.example.whashow.data.RecommandReview
import com.example.whashow.databinding.PerformanceReaultVpItemBinding

class PerformanceViewPagerAdapter (var list: ArrayList<PerformancesByStandard>): RecyclerView.Adapter<PerformanceViewPagerAdapter.RecommandViewHolder>() {

    var performanceList: ArrayList<PerformancesByStandard> =list
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
        val standard=binding.standard
        val rating=binding.ratingBar
        val names=binding.names
        val img=binding.imgPerformance
    }

    override fun onBindViewHolder(holder: PerformanceViewPagerAdapter.RecommandViewHolder, position: Int) {
        holder.rating.rating= performanceList[position].performancesByStandard[0].ratingAverage.toFloat()
        holder.standard.text=performanceList[position].standard
        holder.names.text=performanceList[position].performancesByStandard[0].title
        Glide.with(holder.itemView.context)
            .load(performanceList[position].performancesByStandard[0].poster)
            .override(1500,1500)
            .placeholder(R.drawable.img_poster) // 이미지 로딩 중에 표시될 placeholder 이미지
            .error(R.drawable.img_poster) // 이미지 로딩 실패 시 표시될 이미지
            .into(holder.img)

    }

}