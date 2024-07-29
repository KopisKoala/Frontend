package com.example.whashow.ui.pairing

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.databinding.ListPairingSelectBinding
import com.example.whashow.databinding.ListRecentPairingBinding

class SelectAdapter (var list: ArrayList<Select>): RecyclerView.Adapter<SelectAdapter.SelectViewHolder>() {

    var selectList: ArrayList<Select> =list
        set(value){
            field=value
            notifyDataSetChanged()
        }
    private var selected: Int=0
        set(value){
            field=value
            notifyDataSetChanged()
        }

    interface MyItemClickListener{
        fun onClick(position: Int)

    }

    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener=itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectAdapter.SelectViewHolder{
        return SelectViewHolder(
            ListPairingSelectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class SelectViewHolder(val binding: ListPairingSelectBinding) :
        RecyclerView.ViewHolder(binding.root){
        val img=binding.paringImg
        val name=binding.name
        val stroke=binding.stroke
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SelectAdapter.SelectViewHolder, position: Int) {
        holder.img.setImageResource(selectList[position].img)
        holder.name.text=selectList[position].name
        if (!selectList[position].stroke){
            holder.stroke.visibility=View.GONE
        }
        else {
            holder.stroke.visibility=View.VISIBLE
        }
        holder.itemView.setOnClickListener{
            selectList[selected].stroke=false

            holder.stroke.visibility= View.VISIBLE
            selectList[position].stroke=true
            selected=position
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun removeItem(position: Int) {
        selectList.removeAt(position)
        notifyItemRemoved(position)
    }

}