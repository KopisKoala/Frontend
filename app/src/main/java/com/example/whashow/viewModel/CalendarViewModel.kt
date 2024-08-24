package com.example.whashow.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whashow.data.ReviewX

class CalendarViewModel:ViewModel() {
    class CalendarViewModel : ViewModel() {
        // 날짜를 키로, 리뷰 ID를 값으로 가지는 MutableLiveData 맵
        val dateToReviewIdMap = MutableLiveData<MutableMap<Int, Int>>()

        init {
            // 초기화 시 맵을 빈 맵으로 설정
            dateToReviewIdMap.value = mutableMapOf()
        }

        // 맵에 값을 추가하는 함수
        fun addReviewId(date: Int, reviewId: Int) {
            val map = dateToReviewIdMap.value ?: mutableMapOf()
            map[date] = reviewId
            dateToReviewIdMap.value = map
        }

        // 특정 날짜에 해당하는 리뷰 ID를 가져오는 함수
        fun getReviewId(date: Int): Int? {
            return dateToReviewIdMap.value?.get(date)
        }
    }
}