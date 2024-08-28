package com.example.whashow.ui.pairing

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.R
import com.example.whashow.data.PopularPairDetailResDto
import com.example.whashow.databinding.ListGridPairingResultBinding
import com.example.whashow.databinding.ListTagItemBinding
import com.example.whashow.ui.recommand.Tag
import com.example.whashow.ui.recommand.TagAdapter

class RecentAdapter (var list: ArrayList<PopularPairDetailResDto>): RecyclerView.Adapter<RecentAdapter.RecentViewHolder>() {

    var recentList: ArrayList<PopularPairDetailResDto> =list
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
    ): RecentAdapter.RecentViewHolder{
        return RecentViewHolder(
            ListGridPairingResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class RecentViewHolder(val binding: ListGridPairingResultBinding) :
        RecyclerView.ViewHolder(binding.root){
        val img1=binding.paringImg1
        val img2=binding.paringImg2
        val name=binding.namePairing
        val performance=binding.namePerformance
        val hashtag1=binding.paringFeature
        val hashtag2=binding.paringFeature2
        val order=binding.order
        val reviewNum=binding.reviewNum

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecentAdapter.RecentViewHolder, position: Int) {
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
        holder.order.text=(position+1).toString()
        holder.reviewNum.text=recentList[position].reviewCount.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun removeItem(position: Int) {
        recentList.removeAt(position)
        notifyItemRemoved(position)
    }
    fun updatePairs(newList: List<PopularPairDetailResDto>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

}