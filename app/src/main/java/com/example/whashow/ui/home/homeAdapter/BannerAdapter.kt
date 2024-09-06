package com.example.whashow.ui.home.homeAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.data.Performance
import com.example.whashow.databinding.ListBannerItemBinding

class BannerAdapter(private var list: ArrayList<Performance>) : RecyclerView.Adapter<BannerAdapter.BannerHolder>() {

    interface MyItemClickListener {
        fun onItemClick(banner: Performance)
    }

    private var myItemClickListener: MyItemClickListener? = null

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    inner class BannerHolder(val binding: ListBannerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val img = binding.ivBanner
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerHolder {
        return BannerHolder(
            ListBannerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        val performance = list[position]
        Glide.with(holder.itemView.context)
            .load(performance.poster)
            .into(holder.img)

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
