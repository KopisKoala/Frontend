package com.example.whashow.ui.pairing

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.PairReview
import com.example.whashow.data.Review
import com.example.whashow.data.ReviewLike
import com.example.whashow.databinding.ListPairingSelectBinding
import com.example.whashow.databinding.ListReviewItemBinding
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

            val Call2: Call<ReviewLike> =
                ApiManager.pairingService.deleteLike(
                    "Bearer " + LocalDataSource.getAccessToken()!!, 1
                )
            // 비동기적으로 요청 수행
            Call2.enqueue(object : Callback<ReviewLike> {
                override fun onResponse(
                    call: Call<ReviewLike>,
                    response: Response<ReviewLike>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()?.result
                        Log.d("리뷰 좋아요 삭제 서버", response.body()?.result.toString())

                    } else {
                        // 서버에서 오류 응답을 받은 경우 처리
                        Log.d("리뷰 좋아요 삭제 서버", response.toString())
                    }

                }

                override fun onFailure(call: Call<ReviewLike>, t: Throwable) {
                    // 통신 실패 처리
                    Log.d("리뷰 좋아요 삭제 서버", t.message.toString())
                }

            })


        }
        holder.unchecked.setOnClickListener {
            //reviewList[position].check=true
            holder.checked.visibility=View.VISIBLE
            holder.unchecked.visibility=View.GONE

            val Call2: Call<ReviewLike> =
                ApiManager.pairingService.postLike(
                    "Bearer " + LocalDataSource.getAccessToken()!!, 1
                )
            // 비동기적으로 요청 수행
            Call2.enqueue(object : Callback<ReviewLike> {
                override fun onResponse(
                    call: Call<ReviewLike>,
                    response: Response<ReviewLike>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()?.result
                        Log.d("리뷰 좋아요 서버", response.body()?.result.toString())

                    } else {
                        // 서버에서 오류 응답을 받은 경우 처리
                        Log.d("리뷰 좋아요 서버", response.toString())
                    }

                }

                override fun onFailure(call: Call<ReviewLike>, t: Throwable) {
                    // 통신 실패 처리
                    Log.d("리뷰 좋아요 서버", t.message.toString())
                }

            })


        }
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    fun removeItem(position: Int) {
        reviewList.removeAt(position)
        notifyItemRemoved(position)
    }

}