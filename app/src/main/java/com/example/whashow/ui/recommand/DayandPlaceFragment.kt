package com.example.whashow.ui.recommand

import android.content.ContentValues.TAG
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
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentDayandPlaceBinding
import com.example.whashow.databinding.FragmentGenreBinding
import androidx.core.util.Pair
import androidx.fragment.app.activityViewModels
import com.example.whashow.databinding.ItemSpinnerRecommandSpaceBinding
import com.example.whashow.viewModel.RecommandViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import org.threeten.bp.DayOfWeek
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Calendar.getInstance

class DayandPlaceFragment : BaseFragment<FragmentDayandPlaceBinding>(R.layout.fragment_dayand_place) {
    private val recommandViewModel: RecommandViewModel by activityViewModels()

    private var selectedStartDay: String? = null
    private var selectedEndDay: String? = null
    private var selectedLocation: String? = null
    override fun initStartView() {
        super.initStartView()
        // 장르 정보를 가져와서 UI 업데이트
        recommandViewModel.genre.observe(viewLifecycleOwner) { genre ->
            (activity as MainActivity).binding.backTitle.text = getGenreTitle(genre)
        }
        (activity as MainActivity).binding.toolbar.setBackgroundColor(Color.WHITE)
        (activity as MainActivity).ShowBackandTitle()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()

        val calendarView = binding.calendarView

        // 요일을 한글로 보이게 설정 월..일 순서로 배치해서 캘린더에는 일..월 순서로 보이도록 설정
        binding.calendarView.setWeekDayFormatter(ArrayWeekDayFormatter(resources.getTextArray(R.array.custom_weekdays)));

// 좌우 화살표 사이 연, 월의 폰트 스타일 설정
        binding.calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader)

        // 요일 선택 시 내가 정의한 드로어블이 적용되도록 함
        calendarView.setOnRangeSelectedListener { widget, dates ->
            selectedStartDay = dates[0].date.toString()
            selectedEndDay = dates[dates.size - 1].date.toString()
            Log.e(TAG, "시작일 : $selectedStartDay, 종료일 : $selectedEndDay")

        }

        // 날짜가 단일 선택되었을 때 리스너
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            selectedStartDay = date.date.toString()
            selectedEndDay = ""
            if (!selected) {
                selectedStartDay = ""
            }
        }

        val dayDecorator = DayDecorator(requireContext())
        //val todayDecorator = TodayDecorator(requireContext())
        //val sundayDecorator = SundayDecorator()
        //val saturdayDecorator = SaturdayDecorator()
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

        val outCategoryList = listOf(
            "서울",
            "경기",
            "인천",
            "부산",
            "대구",
            "대전"
        )

        // spinnerRecommandSpace를 레이아웃 파일에서 가져옴
        val spinnerRecommandSpace: Spinner = binding.spinnerRecommandRegister
        spinnerRecommandSpace.adapter= CategorySpinnerAdapter(requireContext(),R.layout.item_spinner_recommand_space,outCategoryList)
        spinnerRecommandSpace.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedLocation = spinnerRecommandSpace.getItemAtPosition(p2).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // 선택되지 않은 경우
            }
        }

        binding.btnPrevious.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
        binding.btnNext.setOnClickListener {
            if (selectedStartDay != null && selectedEndDay != null && selectedLocation != null) {
                // ViewModel에 데이터 저장
                recommandViewModel.setDayAndPlace(
                    startYear = selectedStartDay!!.split("-")[0].toInt(),
                    startMonth = selectedStartDay!!.split("-")[1].toInt(),
                    startDate = selectedStartDay!!.split("-")[2].toInt(),
                    endYear = selectedEndDay!!.split("-")[0].toInt(),
                    endMonth = selectedEndDay!!.split("-")[1].toInt(),
                    endDate = selectedEndDay!!.split("-")[2].toInt(),
                    location = selectedLocation!!
                )
                Log.d("날짜장소", selectedStartDay!!.split("-")[0].toInt().toString())
                Log.d("날짜장소",  selectedStartDay.toString())

                // PriceFragment로 이동
                (activity as MainActivity).addFragment(PriceFragment())
            } else {
                // 에러 처리: 모든 필드가 선택되지 않았을 경우 메시지를 표시
                Toast.makeText(requireContext(), "날짜와 장소를 선택하세요.", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun getGenreTitle(genre: Int?): String {
        return when (genre) {
            0 -> "뮤지컬"
            else -> "연극"
        }
    }

    /* 선택된 날짜의 background를 설정하는 클래스 */
    private inner class DayDecorator(context: Context) : DayViewDecorator {
        private val drawable = ContextCompat.getDrawable(context,R.drawable.calender_selector)
        // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return true
        }

        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        override fun decorate(view: DayViewFacade) {
            view.setSelectionDrawable(drawable!!)
        }
    }

    /* 오늘 날짜의 background를 설정하는 클래스 *//*
    private class TodayDecorator(context: Context): DayViewDecorator {
        private val drawable = ContextCompat.getDrawable(context,R.drawable.calendar_circle_gray)
        private var date = CalendarDay.today()
        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return day?.equals(date)!!
        }
        override fun decorate(view: DayViewFacade?) {
            view?.setBackgroundDrawable(drawable!!)
        }
    }*/

    /* 이번달에 속하지 않지만 캘린더에 보여지는 이전달/다음달의 일부 날짜를 설정하는 클래스 */
    private inner class SelectedMonthDecorator(val selectedMonth : Int) : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day.month != selectedMonth
        }
        override fun decorate(view: DayViewFacade) {
            view.addSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.gray)))
        }
    }

    /* 일요일 날짜의 색상을 설정하는 클래스 *//*
    private class SundayDecorator : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            val sunday = day.date.with(DayOfWeek.SUNDAY).dayOfMonth
            return sunday == day.day
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(object:ForegroundColorSpan(Color.RED){})
        }
    }

    *//* 토요일 날짜의 색상을 설정하는 클래스 *//*
    private class SaturdayDecorator : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            val saturday = day.date.with(DayOfWeek.SATURDAY).dayOfMonth
            return saturday == day.day
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(object:ForegroundColorSpan(Color.BLUE){})
        }
    }
*/

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}
