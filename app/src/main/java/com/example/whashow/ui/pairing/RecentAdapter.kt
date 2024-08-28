package com.example.whashow.ui.pairing

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.databinding.ListGridPairingResultBinding
import com.example.whashow.databinding.ListTagItemBinding
import com.example.whashow.ui.recommand.Tag
import com.example.whashow.ui.recommand.TagAdapter

class RecentAdapter (var list: ArrayList<Performance>): RecyclerView.Adapter<RecentAdapter.RecentViewHolder>() {

    var recentList: ArrayList<Performance> =list
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

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecentAdapter.RecentViewHolder, position: Int) {
        holder.img1.setImageResource(recentList[position].imgPair1)
        holder.img2.setImageResource(recentList[position].imgPair2)
        holder.name.text=recentList[position].names
        holder.performance.text=recentList[position].performance
        holder.hashtag1.text="# "+recentList[position].hashtag[0].tag
        holder.order.text=(position+1).toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun removeItem(position: Int) {
        recentList.removeAt(position)
        notifyItemRemoved(position)
    }

}