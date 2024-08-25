package com.example.whashow.ui.mypage

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.R
import com.example.whashow.data.FavoriteActorResDto
import com.example.whashow.data.GoodsX
import com.example.whashow.databinding.GoodsGridItemBinding
import com.example.whashow.databinding.MyActorListItemBinding

class MyActorAdapter (var list: ArrayList<FavoriteActorResDto>): RecyclerView.Adapter<MyActorAdapter.ActorViewHolder>() {

    var editClick=false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyActorAdapter.ActorViewHolder{
        return ActorViewHolder(
            MyActorListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ActorViewHolder(val binding: MyActorListItemBinding) :
        RecyclerView.ViewHolder(binding.root){
            val profile=binding.profile
            val name=binding.name
            val delete=binding.btnDelete

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyActorAdapter.ActorViewHolder, position: Int) {
        holder.name.text=list[position].actorName
        Glide.with(holder.profile.context)
            .load(R.drawable.img_actor_like)
            .override(1500,1500)
            .placeholder(R.drawable.img_actor_like) // 이미지 로딩 중에 표시될 placeholder 이미지
            .error(R.drawable.img_actor_like) // 이미지 로딩 실패 시 표시될 이미지
            .into(holder.profile)
        holder.delete.setOnClickListener {
            removeItem(position)
        }
        if (editClick){
            holder.delete.visibility= View.VISIBLE
        }
        else {
            holder.delete.visibility= View.GONE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }
}