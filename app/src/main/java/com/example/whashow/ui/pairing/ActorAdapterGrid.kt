package com.example.whashow.ui.pairing

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.data.PairDetailResDto
import com.example.whashow.databinding.ListGridPairingBinding

class ActorAdapterGrid (var list: ArrayList<PairDetailResDto>): RecyclerView.Adapter<ActorAdapterGrid.ActorViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActorAdapterGrid.ActorViewHolder{
        return ActorViewHolder(
            ListGridPairingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ActorViewHolder(val binding: ListGridPairingBinding) :
        RecyclerView.ViewHolder(binding.root){
        val img1=binding.paringImg1
        val img2=binding.paringImg2
        val name=binding.namePairing
        val performance=binding.namePerformance
        val hashtag1=binding.paringFeature
        val hashtag2=binding.paringFeature2
        val review=binding.reviewNum

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ActorAdapterGrid.ActorViewHolder, position: Int) {
        val pair = list[position]
        Glide.with(holder.itemView.context)
            .load(pair.actor1Profile)
            .into(holder.img1)

        Glide.with(holder.itemView.context)
            .load(pair.actor2Profile)
            .into(holder.img2)
        holder.name.text = "${pair.actor1Name} & ${pair.actor2Name}"
        holder.hashtag1.text="# ${pair.hashtag1}"
        holder.review.text = pair.reviewCount.toString()

    }

    override fun getItemCount(): Int {
        return list.size
    }
}