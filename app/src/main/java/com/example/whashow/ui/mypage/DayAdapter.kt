package com.example.whashow.ui.mypage

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.CalendarDayReview
import com.example.whashow.data.CalendarMonth
import com.example.whashow.data.ReviewX
import com.example.whashow.databinding.DayItemBinding
import com.example.whashow.login.LocalDataSource
import com.example.whashow.viewModel.CalendarViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Date


class DayAdapter (val tempMonth:Int, val dayList: MutableList<Date>, val calender:Calendar) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    val ROW =6
    // 날짜를 키로 하고, 리뷰 ID를 값으로 가지는 맵 생성
    val dateToReviewIdMap = mutableMapOf<Int, Int>()
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

    interface MyItemClickListener{
        fun onBtnClick(id:String)

    }

    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener=itemClickListener
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

        val itemYear = calender.get(Calendar.YEAR)
        val itemMonth = String.format("%02d", calender.get(Calendar.MONTH))  // 월은 0부터 시작하므로 +1
        val itemDate = String.format("%02d", calender.get(Calendar.DATE))
        val itemDay= "$itemYear-$itemMonth-$itemDate"
        Log.d("날짜",LocalDataSource.getAccessToken().toString())

        // 선택되지 않은 경우
        val Call: Call<CalendarMonth> =
            ApiManager.mypageService.getMonthReview(
                "Bearer " + LocalDataSource.getAccessToken(), itemDay
            )
        // 비동기적으로 요청 수행
        Call.enqueue(object : Callback<CalendarMonth> {
            override fun onResponse(
                call: Call<CalendarMonth>,
                response: Response<CalendarMonth>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.result
                    if (data!=null){
                        val reviewDayList=data.reviewList as ArrayList<ReviewX>
                        for (i in 0 until reviewDayList.size){

                            if (reviewDayList[i].performanceDate.toInt()==dayList[holder.adapterPosition].date){
                                // 날짜를 키로 리뷰 ID를 맵에 저장
                                dateToReviewIdMap[dayList[holder.adapterPosition].date] = reviewDayList[i].id
                                Glide.with(holder.itemView.context)
                                    .load(reviewDayList[i].poster)
                                    .override(1500,1500)
                                    .placeholder(R.drawable.img_poster_small) // 이미지 로딩 중에 표시될 placeholder 이미지
                                    .error(R.drawable.img_poster_small) // 이미지 로딩 실패 시 표시될 이미지
                                    .into(holder.dayBg)

                            }
                        }
                    }
                    Log.d("월 목록 조회", data.toString())
                    Log.d("월 목록 조회 서버", response.body()?.result.toString())

                } else {
                    // 서버에서 오류 응답을 받은 경우 처리
                    Log.d("월 목록 조회 서버", response.toString())
                }

            }

            override fun onFailure(call: Call<CalendarMonth>, t: Throwable) {
                // 통신 실패 처리
                Log.d("월 목록 조회 서버", t.message.toString())
            }

        })

        // 클릭 이벤트 설정
        holder.itemView.setOnClickListener {
            Log.d("아이디", dateToReviewIdMap[dayList[position].date].toString())
            myItemClickListener.onBtnClick(dateToReviewIdMap[dayList[position].date].toString())
        }

    }


    override fun getItemCount(): Int {
        return ROW*7
    }
}