package com.example.whashow.ui.pairing

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.R
import com.example.whashow.data.Review
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
        holder.img.setImageResource(R.drawable.img_profile)
        holder.name.text=reviewList[position].writer
        holder.rating.rating=reviewList[position].rating.toFloat()
        holder.feature.text=reviewList[position].hashTag
        holder.review.text=reviewList[position].content
        holder.likeNum.text=reviewList[position].likeCount.toString()
        holder.checked.setOnClickListener {
            //reviewList[position].check=false
            holder.checked.visibility=View.GONE
            holder.unchecked.visibility=View.VISIBLE
        }
        holder.unchecked.setOnClickListener {
            //reviewList[position].check=true
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