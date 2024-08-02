package com.example.whashow.ui.home.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.whashow.databinding.ListBannerItemBinding
import com.example.whashow.ui.home.Banner

class BannerAdapter(var list: ArrayList<Banner>) : RecyclerView.Adapter<BannerAdapter.BannerHolder>() {

    var BannerList: ArrayList<Banner> = list
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface MyItemClickListener {
        fun onItemClick(banner: Banner)
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
        holder.img.setImageResource(list[position].img)
        holder.itemView.setOnClickListener {
            myItemClickListener?.onItemClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
