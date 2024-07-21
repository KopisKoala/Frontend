package com.example.whashow.ui.recommand

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentDayandPlaceBinding
import com.example.whashow.databinding.FragmentGenreBinding
import androidx.core.util.Pair
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Calendar.getInstance

class DayandPlaceFragment : BaseFragment<FragmentDayandPlaceBinding>(R.layout.fragment_dayand_place) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.backTitle.text = "서버연결"
        (activity as MainActivity).ShowBackandTitle()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}