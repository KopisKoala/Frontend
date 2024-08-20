package com.example.whashow.ui.mypage

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentHomeBinding
import com.example.whashow.databinding.FragmentPerformanceCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter

class PerformanceCalendarFragment : BaseFragment<FragmentPerformanceCalendarBinding>(R.layout.fragment_performance_calendar) {
    override fun initStartView() {
        super.initStartView()
    }

    // 미리 지정된 날짜 리스트 (예시로 몇 가지 날짜를 추가)
    private val selectedDates = mutableListOf(
        CalendarDay.from(2024, 8, 20),  // 2024년 8월 20일
        CalendarDay.from(2024, 8, 1),   // 2024년 9월 1일
        CalendarDay.from(2024, 8, 15)  // 2024년 10월 15일
    )

    override fun initDataBinding() {
        super.initDataBinding()

        val calendarView = binding.calendarView

        // 요일을 한글로 보이게 설정 월..일 순서로 배치해서 캘린더에는 일..월 순서로 보이도록 설정
        binding.calendarView.setWeekDayFormatter(ArrayWeekDayFormatter(resources.getTextArray(R.array.custom_weekdays)));

        // 날짜가 단일 선택되었을 때 리스너
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->

        }

        val dayDecorator = DayDecorator(requireContext())
        var selectedMonthDecorator = SelectedMonthDecorator(CalendarDay.today().month)

// 캘린더에 Decorator 추가
        binding.calendarView.addDecorators(dayDecorator, selectedMonthDecorator)

// 좌우 화살표 가운데의 연/월이 보이는 방식 지정
        binding.calendarView.setTitleFormatter { day ->
            val inputText = day.date
            val calendarHeaderElements = inputText.toString().split("-")
            val calendarHeaderBuilder = StringBuilder()

            calendarHeaderBuilder.append(calendarHeaderElements[0]).append("년 ")
                .append(calendarHeaderElements[1]).append("월")

            calendarHeaderBuilder.toString()
        }

// 캘린더에 보여지는 Month가 변경된 경우
        binding.calendarView.setOnMonthChangedListener { widget, date ->
            // 기존에 설정되어 있던 Decorators 초기화
            binding.calendarView.removeDecorators()
            binding.calendarView.invalidateDecorators()

            // Decorators 추가
            selectedMonthDecorator = SelectedMonthDecorator(date.month)
            binding.calendarView.addDecorators(dayDecorator, selectedMonthDecorator)
        }
    }

    /* 원하는 날짜들에만 배경색을 설정하는 클래스 */
    private inner class DayDecorator(context: Context) : DayViewDecorator {

        private val drawable = ContextCompat.getDrawable(context,R.drawable.img_poster_small)
        // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return selectedDates.contains(day)
        }

        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        override fun decorate(view: DayViewFacade) {

            view.setSelectionDrawable(drawable!!)
        }
    }

    /* 이번달에 속하지 않지만 캘린더에 보여지는 이전달/다음달의 일부 날짜를 설정하는 클래스 */
    private inner class SelectedMonthDecorator(val selectedMonth : Int) : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day.month != selectedMonth
        }
        override fun decorate(view: DayViewFacade) {
            view.addSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.white)))
        }
    }


    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}