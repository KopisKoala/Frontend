package com.example.whashow.ui.pairing

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.databinding.ListPairingSelectBinding
import com.example.whashow.databinding.ListReviewItemBinding

class ReviewAdapter (var list: ArrayList<Review>): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    var reviewList: ArrayList<Review> =list
        set(value){
            field=value
            notifyDataSetChanged()
        }

    interface MyItemClickListener{
        fun onClick(position: Int)

    }

    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener=itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewAdapter.ReviewViewHolder{
        return ReviewViewHolder(
            ListReviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ReviewViewHolder(val binding: ListReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        val img =binding.profile
        val name=binding.name
        val rating=binding.ratingBar
        val feature=binding.paringFeature
        val review=binding.review
        val checked=binding.checked
        val unchecked=binding.unchecked
        val likeNum=binding.likeNum
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ReviewAdapter.ReviewViewHolder, position: Int) {
        holder.img.setImageResource(reviewList[position].img)
        holder.name.text=reviewList[position].name
        holder.rating.rating=reviewList[position].rating
        holder.feature.text=reviewList[position].feature
        holder.review.text=reviewList[position].review
        holder.likeNum.text=reviewList[position].likeNum
        holder.checked.setOnClickListener {
            reviewList[position].check=false
            holder.checked.visibility=View.GONE
            holder.unchecked.visibility=View.VISIBLE
        }
        holder.unchecked.setOnClickListener {
            reviewList[position].check=true
            holder.checked.visibility=View.VISIBLE
            holder.unchecked.visibility=View.GONE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun removeItem(position: Int) {
        reviewList.removeAt(position)
        notifyItemRemoved(position)
    }

}