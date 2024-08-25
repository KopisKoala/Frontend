package com.example.whashow.ui.pairing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.databinding.ListGridPairingBinding
import com.example.whashow.databinding.ListGridRecommandItemBinding
import com.example.whashow.ui.recommand.RecommandResult

class ActorAdapterGrid (var list: ArrayList<Actor>): RecyclerView.Adapter<ActorAdapterGrid.ActorViewHolder>() {

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
        val order=binding.order
    }

    override fun onBindViewHolder(holder: ActorAdapterGrid.ActorViewHolder, position: Int) {
        holder.img1.setImageResource(list[position].imgPair1)
        holder.img2.setImageResource(list[position].imgPair2)
        holder.name.text=list[position].names
        holder.performance.text=list[position].performance
        holder.hashtag1.text="# "+list[position].hashtag[0].tag
        holder.review.text=list[position].review
        holder.order.text=(position+1).toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}