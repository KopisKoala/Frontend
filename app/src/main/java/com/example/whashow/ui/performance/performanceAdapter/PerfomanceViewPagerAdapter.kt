package com.example.whashow.ui.performance.performanceAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.R
import com.example.whashow.data.PerformancesByStandard
import com.example.whashow.databinding.PerformanceReaultVpItemBinding

class PerformanceViewPagerAdapter (var list: ArrayList<PerformancesByStandard>): RecyclerView.Adapter<PerformanceViewPagerAdapter.RecommandViewHolder>() {

    var performanceList: ArrayList<PerformancesByStandard> =list
        set(value){
            field=value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) : RecommandViewHolder {
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

    override fun onBindViewHolder(holder: RecommandViewHolder, position: Int) {
        holder.rating.rating = if (performanceList.isNotEmpty() && performanceList.size > position && performanceList[position].performancesByStandard.isNotEmpty()) {
            performanceList[position].performancesByStandard[0].ratingAverage
        } else {
            Log.d("공연 목록 조회", performanceList.toString())
            0f // 기본 값, 필요에 따라 변경
        }

        holder.standard.text = if (performanceList.isNotEmpty() && performanceList.size > position) {
            performanceList[position].standard ?: "정보 없음" // 기본 텍스트, 필요에 따라 변경
        } else {
            "정보 없음" // 기본 텍스트
        }

        holder.names.text = if (performanceList.isNotEmpty() && performanceList.size > position && performanceList[position].performancesByStandard.isNotEmpty()) {
            performanceList[position].performancesByStandard[0].title ?: "제목 없음" // 기본 텍스트, 필요에 따라 변경
        } else {
            "제목 없음" // 기본 텍스트
        }

        val posterUrl = if (performanceList.isNotEmpty() && performanceList.size > position && performanceList[position].performancesByStandard.isNotEmpty()) {
            performanceList[position].performancesByStandard[0].poster
        } else {
            null
        }

        Glide.with(holder.itemView.context)
            .load(posterUrl)
            .override(1500, 1500)
            .placeholder(R.drawable.img_poster) // 이미지 로딩 중에 표시될 placeholder 이미지
            .error(R.drawable.img_poster) // 이미지 로딩 실패 시 표시될 이미지
            .into(holder.img)

    }
    fun updatePerformance(newList: List<PerformancesByStandard>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

}