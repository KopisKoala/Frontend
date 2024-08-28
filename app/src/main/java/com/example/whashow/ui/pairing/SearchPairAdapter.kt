package com.example.whashow.ui.pairing

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.R
import com.example.whashow.data.PairDetailListResDto
import com.example.whashow.data.PairDetailResDto
import com.example.whashow.data.PopularPairDetailResDto
import com.example.whashow.databinding.ListGridPairingBinding
import com.example.whashow.databinding.ListGridPairingResultBinding

class SearchPairAdapter (var list: ArrayList<PairDetailResDto>): RecyclerView.Adapter<SearchPairAdapter.SearchPairViewHolder>() {

    var recentList: ArrayList<PairDetailResDto> =list
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchPairAdapter.SearchPairViewHolder{
        return SearchPairViewHolder(
            ListGridPairingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class SearchPairViewHolder(val binding: ListGridPairingBinding) :
        RecyclerView.ViewHolder(binding.root){
        val img1=binding.paringImg1
        val img2=binding.paringImg2
        val name=binding.namePairing
        val performance=binding.namePerformance
        val hashtag1=binding.paringFeature
        val hashtag2=binding.paringFeature2
        val reviewNum=binding.reviewNum

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SearchPairAdapter.SearchPairViewHolder, position: Int) {
        Log.d("인기 페어 조회 서버", list.toString())
        Log.d("인기 페어 조회 서버", recentList.toString())
        Glide.with(holder.itemView.context)
            .load(recentList[position].actor1Profile)
            .override(1500,1500)
            .placeholder(R.drawable.img_actor_like) // 이미지 로딩 중에 표시될 placeholder 이미지
            .error(R.drawable.img_actor_like) // 이미지 로딩 실패 시 표시될 이미지
            .into(holder.img1)
        Glide.with(holder.itemView.context)
            .load(recentList[position].actor2Profile)
            .override(1500,1500)
            .placeholder(R.drawable.img_actor_like) // 이미지 로딩 중에 표시될 placeholder 이미지
            .error(R.drawable.img_actor_like) // 이미지 로딩 실패 시 표시될 이미지
            .into(holder.img2)
        holder.name.text=recentList[position].actor1Name+" "+recentList[position].actor2Name
        holder.performance.text=recentList[position].title
        holder.hashtag1.text="# "+recentList[position].hashtag1
        holder.hashtag2.text="# "+recentList[position].hashtag2
        holder.reviewNum.text=recentList[position].reviewCount.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun removeItem(position: Int) {
        recentList.removeAt(position)
        notifyItemRemoved(position)
    }
    fun updatePairs(newList: List<PairDetailResDto>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

}