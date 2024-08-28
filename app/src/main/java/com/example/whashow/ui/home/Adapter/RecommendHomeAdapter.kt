package com.example.whashow.ui.home.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.data.Performance
import com.example.whashow.databinding.ListGridRecommandItemBinding

class RecommendHomeAdapter(private var list: ArrayList<Performance>) : RecyclerView.Adapter<RecommendHomeAdapter.RecommendHolder>() {

    interface MyItemClickListener {
        fun onItemClick(banner: Performance)
    }

    private var myItemClickListener: MyItemClickListener? = null

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    inner class RecommendHolder(val binding: ListGridRecommandItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val img = binding.recommendPoster
        val title = binding.title
        val hall = binding.hall
        val duration = binding.duration

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendHolder {
        return RecommendHolder(
            ListGridRecommandItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecommendHolder, position: Int) {
        val performance = list[position]
        Glide.with(holder.itemView.context)
            .load(performance.poster)
            .into(holder.img)
        holder.title.text = performance.title
        holder.hall.text = performance.hall
        holder.duration.text = performance.duration

        holder.itemView.setOnClickListener {
            myItemClickListener?.onItemClick(performance)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updatePerformances(newList: List<Performance>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}