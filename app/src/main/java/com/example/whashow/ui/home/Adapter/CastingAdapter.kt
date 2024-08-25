package com.example.whashow.ui.home.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.R
import com.example.whashow.data.DetailActor
import com.example.whashow.databinding.ListPerformanceDetailReviewItemBinding

class CastingAdapter(var list: ArrayList<DetailActor>) : RecyclerView.Adapter<CastingAdapter.CastingHolder>() {

    interface MyItemClickListener {
        fun onDeleteClick(position: Int)
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    inner class CastingHolder(val binding: ListPerformanceDetailReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(detailActor: DetailActor) {

            binding.nameTxt.text = detailActor.actorName
            binding.roleTxt.text = detailActor.characterName


            Glide.with(binding.root.context)
                .load(detailActor.actorProfile)
                .into(binding.profileImg)


            if (detailActor.isFavoriteActor == "Y") {
                binding.heartIcon.setImageResource(R.drawable.heart_purple)
            } else {
                binding.nonHeartIcon.setImageResource(R.drawable.heart_gray)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastingHolder {
        val binding = ListPerformanceDetailReviewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CastingHolder(binding)
    }

    override fun onBindViewHolder(holder: CastingHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: ArrayList<DetailActor>) {
        list = newList
        notifyDataSetChanged()
    }
}
