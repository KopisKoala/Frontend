package com.example.whashow.ui.home.Adapter

import Actor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.databinding.ListGridSearchActorItemBinding


class ActorAdapter(private var list: ArrayList<Actor>) : RecyclerView.Adapter<ActorAdapter.ActorHolder>() {

    interface MyItemClickListener {
        fun onItemClick(actor: Actor)
    }

    private var myItemClickListener: MyItemClickListener? = null

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    inner class ActorHolder(val binding: ListGridSearchActorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val poster = binding.profileImg
        val name = binding.nameTxt
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
        return ActorHolder(
            ListGridSearchActorItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        val actor = list[position]
        Glide.with(holder.itemView.context)
            .load(actor.actorProfile)
            .into(holder.poster)
        holder.name.text = actor.actorName

        holder.itemView.setOnClickListener {
            myItemClickListener?.onItemClick(actor)
        }
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
