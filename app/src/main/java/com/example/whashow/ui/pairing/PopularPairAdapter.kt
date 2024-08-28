package com.example.whashow.ui.pairing

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.data.PopularPairDetailResDto
import com.example.whashow.databinding.ListPopularPairingBinding

class PopularPairAdapter (var list: ArrayList<PopularPairDetailResDto>): RecyclerView.Adapter<PopularPairAdapter.PopularPairViewHolder>() {

    var PopularPairList: ArrayList<PopularPairDetailResDto> =list
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
    ): PopularPairAdapter.PopularPairViewHolder{
        return PopularPairViewHolder(
            ListPopularPairingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class PopularPairViewHolder(val binding: ListPopularPairingBinding) :
        RecyclerView.ViewHolder(binding.root){
        val img1=binding.paringImg1
        val img2=binding.paringImg2
        val name=binding.namePairing
        val title=binding.namePerformance
        val hashtag1=binding.paringFeature
        val hashtag2=binding.paringFeature2


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PopularPairAdapter.PopularPairViewHolder, position: Int) {
        val pair = list[position]
        Glide.with(holder.itemView.context)
            .load(pair.actor1Profile)
            .into(holder.img1)
        Glide.with(holder.itemView.context)
            .load(pair.actor2Profile)
            .into(holder.img2)

        holder.name.text="${pair.actor1Name} & ${pair.actor2Name}"
        holder.title.text=pair.title
        holder.hashtag1.text="#${pair.hashtag1}"
        holder.hashtag2.text="#${pair.hashtag2}"
    }

    override fun getItemCount(): Int {
        return list.size
    }

}