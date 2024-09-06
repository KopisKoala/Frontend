package com.example.whashow.ui.home.homeAdapter

import Performance
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.whashow.databinding.ListGridSearchPerformanceItemBinding

class PerformanceAdapter(var list: ArrayList<Performance>) : RecyclerView.Adapter<PerformanceAdapter.PerformanceHolder>() {

    interface MyItemClickListener {
        fun onItemClick(performance: Performance)
    }

    private var myItemClickListener: MyItemClickListener? = null

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    inner class PerformanceHolder(val binding: ListGridSearchPerformanceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val img = binding.poster
        val title = binding.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformanceHolder {
        return PerformanceHolder(
            ListGridSearchPerformanceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PerformanceHolder, position: Int) {
        val performance = list[position]
        Glide.with(holder.itemView.context)
            .load(performance.poster)
            .into(holder.img)
        holder.title.text = performance.title

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