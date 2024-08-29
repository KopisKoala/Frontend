package com.example.whashow.ui.mypage

import android.app.DatePickerDialog
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.ReviewRequest
import com.example.whashow.databinding.FragmentMakeReviewBinding
import com.example.whashow.ui.home.HomeFragment
import java.util.Calendar

class MakeReviewFragment : BaseFragment<FragmentMakeReviewBinding>(R.layout.fragment_make_review) {

    private val makePairViewModel: MakePairViewModel by viewModels()
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private var perfId: Int? = null
    private var title: String? = null
    private lateinit var calendar: Calendar
    private var selectedPairId: Int? = null  // 변수 추가

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.backTitle.text = "리뷰작성"
        (activity as MainActivity).ShowBackandTitle()

        calendar = Calendar.getInstance()
        spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, mutableListOf())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPair.adapter = spinnerAdapter
        spinnerAdapter.add("관람한 페어를 선택해 주세요")

        perfId = arguments?.getInt("perfId")
        title = arguments?.getString("title")
        Log.d("titleReview", "$title")
        binding.performanceTitle.text = title
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE

        binding.btnSave.setOnClickListener {
            saveReview()
        }

        makePairViewModel.pairList.observe(viewLifecycleOwner, Observer { pairDataList ->
            val pairNames = pairDataList.map { "${it.actor1} & ${it.actor2}" }
            spinnerAdapter.clear()
            spinnerAdapter.addAll(pairNames)
            spinnerAdapter.notifyDataSetChanged()
        })
        makePairViewModel.reviewSaveStatus.observe(viewLifecycleOwner, Observer { statusMessage ->
            // 리뷰 저장 성공 시 홈 화면으로 이동
            statusMessage?.let {
                if (it.contains("리뷰가 생성되었습니다.")) {
                    (activity as MainActivity).changeFragment(HomeFragment())
                    Toast.makeText(requireContext(), "리뷰가 정상적으로 생성되었습니다!", Toast.LENGTH_SHORT).show()

                } else {
                    // 리뷰 저장 실패 시 처리
                    Log.e("MakeReviewFragment", "리뷰 저장 실패: $statusMessage")
                }
            }
        })


        updateDateDisplay()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE

        binding.spinnerPair.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                perfId?.let {
                    makePairViewModel.fetchPairData(it)
                }
            }
            false
        }

        binding.dateTextView.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                updateDateDisplay()
            },
            year, month, day
        ).show()
    }

    private fun updateDateDisplay() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        binding.dateTextView.text = getString(R.string.date_format, year, month, day)
    }

    private fun saveReview() {
        val performanceRating = binding.ratingBar.rating.toInt()
        val pairRating = binding.ratingPair.rating.toInt()
        val hashTag = binding.etHashtag.text.toString()
        val content = binding.etReview.text.toString()
        val performanceDate = getFormattedDate()

        val selectedPairPosition = binding.spinnerPair.selectedItemPosition
        if (selectedPairPosition > 0) {
            selectedPairId = makePairViewModel.pairList.value?.get(selectedPairPosition - 1)?.pairId
        }

        if (selectedPairId == null) {
            // 선택된 페어가 없거나 페어 ID를 찾을 수 없는 경우 처리
            Log.e("MakeReviewFragment", "페어를 선택해 주세요.")
            return
        }

        val reviewRequest = ReviewRequest(
            performanceId = perfId ?: return,
            performanceRating = performanceRating,
            pairId = selectedPairId ?: return,
            pairRating = pairRating,
            hashTag = hashTag,
            content = content,
            performanceDate = performanceDate
        )

        makePairViewModel.saveReview(
            performanceId = reviewRequest.performanceId,
            performanceRating = reviewRequest.performanceRating,
            pairId = reviewRequest.pairId,
            pairRating = reviewRequest.pairRating,
            hashTag = reviewRequest.hashTag,
            content = reviewRequest.content,
            performanceDate = reviewRequest.performanceDate
        )
    }

    private fun getFormattedDate(): String {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return "$year-${"%02d".format(month)}-${"%02d".format(day)}"
    }
}
