package com.example.whashow.ui.mypage

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whashow.data.ReviewX
import com.example.whashow.databinding.ListTagItemBinding
import com.example.whashow.databinding.MonthItemBinding
import com.example.whashow.ui.recommand.Tag
import java.util.Calendar
import java.util.Date

class MonthAdapter(var list:ArrayList<ReviewX>) : RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {

    var reviewlist: ArrayList<ReviewX> =list
        set(value){
            field=value
            notifyDataSetChanged()
        }
    var calendar: Calendar = Calendar.getInstance()
    private lateinit var dayAdapter:DayAdapter

    interface MyItemClickListener{
        fun onBtnClick(id:String)

    }

    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener=itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MonthAdapter.MonthViewHolder {
        return MonthViewHolder(
            MonthItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class MonthViewHolder(val binding: MonthItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        val monthRV=binding.monthRecycler
        val title=binding.title
    }


    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {

        /*holder.next.setOnClickListener {
            myItemClickListener.onBtnClick(true,position)
        }
        holder.previous.setOnClickListener{
            myItemClickListener.onBtnClick(false,position)
        }*/

        //달 구하기
        calendar.time = Date() //현재 날짜 초기화
        calendar.set(Calendar.DAY_OF_MONTH,1) //스크롤시 현재 월의 1일로 이동
        calendar.add(Calendar.MONTH , position) //스크롤시 포지션 만큼 달이동


        //현재 날짜 출력
        holder.title.setText("${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월")

        val tempMonth = calendar.get(Calendar.MONTH)

        //일 구하기
        //6주 7일로 날짜를 표시
        var dayList: MutableList<Date> = MutableList(6 * 7 ) { Date() }

        for(i in 0..5) { //주
            for (k in 0..6) { //요일
                //각 달의 요일만큼 캘린더에 보여진다
                //요일 표시
                calendar.add(Calendar.DAY_OF_MONTH, (1 - calendar.get(Calendar.DAY_OF_WEEK)) + k)
                dayList[i * 7 + k] = calendar.time //배열 인덱스 만큼 요일 데이터 저장
            }
            //주 표시
            calendar.add(Calendar.WEEK_OF_MONTH, 1)
        }

        holder.monthRV.layoutManager = GridLayoutManager(holder.itemView.context,7)
        dayAdapter= DayAdapter(tempMonth,dayList,calendar)
        dayAdapter.setMyItemClickListener(object:DayAdapter.MyItemClickListener{
            override fun onBtnClick(id: String) {
                myItemClickListener.onBtnClick(id)
            }

        })
        holder.monthRV.adapter =dayAdapter
        dayAdapter.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE / 2
    }


}