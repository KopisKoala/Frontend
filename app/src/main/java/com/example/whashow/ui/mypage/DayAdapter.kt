package com.example.whashow.ui.mypage

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.R
import com.example.whashow.data.ReviewX
import com.example.whashow.databinding.DayItemBinding
import com.example.whashow.databinding.MonthItemBinding
import java.util.Date

class DayAdapter (val tempMonth:Int, val dayList: MutableList<Date>, val reviewDayList:ArrayList<ReviewX>) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    val ROW =6
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DayAdapter.DayViewHolder {
        return DayViewHolder(
            DayItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class DayViewHolder(val binding: DayItemBinding):
        RecyclerView.ViewHolder(binding.root){
            val day=binding.itemDayText
            val dayBg=binding.itemDayLayout
        }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {

        //날짜 표시
        holder.day.text = dayList[position].date.toString()
        if(tempMonth != dayList[position].month) {
            holder.day.alpha=0.4f
        }

        //토요일이면 파란색 || 일요일이면 빨간색으로 색상표시
        if((position + 1) % 7 == 0) {
            holder.day.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.gray))
        } else if (position == 0 || position % 7 == 0) {
            holder.day.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.purple))
        }

        for (i in 0 until reviewDayList.size){
            if (reviewDayList[i].performanceDate.toInt()==dayList[position].date){
                holder.dayBg.setBackground(ContextCompat.getDrawable(holder.itemView.context,reviewDayList[i].poster.toInt()))
            }
        }
    }


    override fun getItemCount(): Int {
        return ROW*7
    }
}