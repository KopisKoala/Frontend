package com.example.whashow.ui.home.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.databinding.ListGridRecommandItemBinding
import com.example.whashow.ui.home.Popular

class PopularAdapter (var list: ArrayList<Popular>) : RecyclerView.Adapter<PopularAdapter.PopularHolder>(){

    var PopularList: ArrayList<Popular> =list
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
    inner class PopularHolder(val binding: ListGridRecommandItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        val img=binding.recommandPoster
        val title=binding.performanceTitle
        val space=binding.performanceDetail
        val rating=binding.ratingBar
        val review=binding.review
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularHolder {
        return PopularHolder(
            ListGridRecommandItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: PopularHolder, position: Int) {
        holder.img.setImageResource(list[position].img)
        holder.title.text=list[position].title
        holder.space.text=list[position].space
        holder.rating.rating=list[position].rating
        holder.review.text=list[position].review
    }

    override fun getItemCount(): Int {
        return minOf(list.size, 5)  // 최대 2개의 아이템만 표시
    }

}

