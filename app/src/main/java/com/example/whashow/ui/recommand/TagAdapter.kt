package com.example.whashow.ui.recommand

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.databinding.ListGridRecommandItemBinding
import com.example.whashow.databinding.ListTagItemBinding

class TagAdapter (var list: ArrayList<Tag>): RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    var tagList: ArrayList<Tag> =list
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
    ): TagAdapter.TagViewHolder{
        return TagViewHolder(
            ListTagItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class TagViewHolder(val binding: ListTagItemBinding) :
        RecyclerView.ViewHolder(binding.root){
            val name=binding.tagText
            val close=binding.close
    }

    override fun onBindViewHolder(holder: TagAdapter.TagViewHolder, position: Int) {
        holder.name.text=list[position].name
        holder.close.setOnClickListener {
            myItemClickListener.onDeleteClick(position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun removeItem(position: Int) {
        tagList.removeAt(position)
        notifyItemRemoved(position)
    }

}