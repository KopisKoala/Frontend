package com.example.whashow.ui.home.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.databinding.BannerItemBinding
import com.example.whashow.ui.home.Banner

class BannerAdapter(var list: ArrayList<Banner>) : RecyclerView.Adapter<BannerAdapter.BannerHolder>(){

    var BannerList: ArrayList<Banner> =list
        set(value){
            field=value
            notifyDataSetChanged()
        }

    interface MyItemClickListener{
        fun onDeleteClick(position: Int)
    }

    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener=itemClickListener
    }
    inner class BannerHolder(val binding: BannerItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        val img=binding.ivBanner
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerHolder {
        return BannerHolder(
            BannerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        holder.img.setImageResource(list[position].img)
    }

    override fun getItemCount(): Int {
        return minOf(list.size, 5)  // 최대 2개의 아이템만 표시
    }





}