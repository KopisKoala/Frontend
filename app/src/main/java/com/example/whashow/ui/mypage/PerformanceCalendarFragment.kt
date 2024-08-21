package com.example.whashow.ui.mypage

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.CalendarMonth
import com.example.whashow.data.PairReview
import com.example.whashow.data.Review
import com.example.whashow.data.ReviewX
import com.example.whashow.databinding.FragmentHomeBinding
import com.example.whashow.databinding.FragmentPerformanceCalendarBinding
import com.example.whashow.login.LocalDataSource
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerformanceCalendarFragment : BaseFragment<FragmentPerformanceCalendarBinding>(R.layout.fragment_performance_calendar) {

    private lateinit var monthAdapter:MonthAdapter
    var position:Int=Int.MAX_VALUE/2
    override fun initStartView() {
        super.initStartView()
    }

    val reviewList = arrayListOf(
        ReviewX(id = 1, performanceDate = "4", poster = R.drawable.img_poster_small.toString()),
        ReviewX(id = 2, performanceDate = "22", poster = R.drawable.img_poster_small2.toString()),
        ReviewX(id = 3, performanceDate = "26", poster = R.drawable.img_poster_small3.toString())
    )


    override fun initDataBinding() {
        super.initDataBinding()

        monthAdapter=MonthAdapter(reviewList)
        binding.calRecycler.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.calRecycler.adapter=monthAdapter
        binding.calRecycler.scrollToPosition(position)
        val snap=PagerSnapHelper()
        snap.attachToRecyclerView(binding.calRecycler)



    }


    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}