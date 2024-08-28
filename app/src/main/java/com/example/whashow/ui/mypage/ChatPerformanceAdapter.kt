package com.example.whashow.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.databinding.ListChatperformanceItemBinding

class ChatPerformanceAdapter (var list: ArrayList<ChatPerformance>) : RecyclerView.Adapter<ChatPerformanceAdapter.ChatPerformanceHolder>(){

    var ChatPerformanceList: ArrayList<ChatPerformance> =list
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
    inner class ChatPerformanceHolder(val binding: ListChatperformanceItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        val chatname=binding.chatPerformanceTitle
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatPerformanceHolder {
        return ChatPerformanceHolder(
            ListChatperformanceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: ChatPerformanceHolder, position: Int) {
        holder.chatname.text=list[position].name
    }

    override fun getItemCount(): Int {
        return minOf(list.size, 4)
    }
}