package com.example.whashow.ui.home.homeAdapter

import Actor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.R
import com.example.whashow.databinding.ListGridSearchActorItemBinding


class ActorAdapter(private var list: ArrayList<Actor>) : RecyclerView.Adapter<ActorAdapter.ActorHolder>() {

    interface MyItemClickListener {
        fun onLikeClick(actor : Actor, isLike : String)
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    inner class ActorHolder(val binding: ListGridSearchActorItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(actor: Actor) {
            binding.nameTxt.text = actor.actorName
            Glide.with(binding.root.context)
                .load(actor.actorProfile)
                .into(binding.profileImg)

            if(actor.isFavoriteActor == "Y") {
                binding.heartIcon.setImageResource(R.drawable.heart_purple)
            } else {
                binding.heartIcon.setImageResource(R.drawable.heart_gray)
            }

            binding.heartIcon.setOnClickListener {
                myItemClickListener.onLikeClick(actor, actor.isFavoriteActor)
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
        val binding = ListGridSearchActorItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ActorHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateActors(newList: List<Actor>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
