package com.example.whashow.ui.recommand

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.databinding.ItemSpinnerRecommandSpaceBinding
import com.example.whashow.databinding.ListGridRecommandItemBinding

class RecommandAdapterGrid (var list: ArrayList<RecommandResult>): RecyclerView.Adapter<RecommandAdapterGrid.RecommandViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommandAdapterGrid.RecommandViewHolder{
        return RecommandViewHolder(
            ListGridRecommandItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class RecommandViewHolder(val binding: ListGridRecommandItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        val img=binding.recommandPoster
        val title=binding.performanceTitle
        val space=binding.performanceDetail
        val rating=binding.ratingBar
        val review=binding.review
    }

    override fun onBindViewHolder(holder: RecommandAdapterGrid.RecommandViewHolder, position: Int) {
        holder.img.setImageResource(list[position].img)
        holder.title.text=list[position].title
        holder.space.text=list[position].space
        holder.rating.rating=list[position].rating
        holder.review.text=list[position].review
    }

    override fun getItemCount(): Int {
        return list.size
    }
}