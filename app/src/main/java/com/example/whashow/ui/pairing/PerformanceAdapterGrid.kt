package com.example.whashow.ui.pairing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.databinding.ListGridPairingBinding

class PerformanceAdapterGrid (var list: ArrayList<Performance>): RecyclerView.Adapter<PerformanceAdapterGrid.PerformanceViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PerformanceAdapterGrid.PerformanceViewHolder{
        return PerformanceViewHolder(
            ListGridPairingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class PerformanceViewHolder(val binding: ListGridPairingBinding) :
        RecyclerView.ViewHolder(binding.root){
        val img1=binding.paringImg1
        val img2=binding.paringImg2
        val name=binding.namePairing
        val performance=binding.namePerformance
        val hashtag1=binding.paringFeature
        val hashtag2=binding.paringFeature2
        val review=binding.reviewNum
    }

    override fun onBindViewHolder(holder: PerformanceAdapterGrid.PerformanceViewHolder, position: Int) {
        holder.img1.setImageResource(list[position].imgPair1)
        holder.img2.setImageResource(list[position].imgPair2)
        holder.name.text=list[position].names
        holder.performance.text=list[position].performance
        holder.hashtag1.text="# "+list[position].hashtag[0].tag
        holder.review.text=list[position].review
    }

    override fun getItemCount(): Int {
        return list.size
    }
}