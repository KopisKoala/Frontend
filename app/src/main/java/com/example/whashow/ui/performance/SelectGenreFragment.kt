package com.example.whashow.ui.performance

import android.graphics.Color
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentGenreBinding
import com.example.whashow.viewModel.RecommandViewModel


class SelectGenreFragment : BaseFragment<FragmentGenreBinding>(R.layout.fragment_genre) {

    private val recommandViewModel: RecommandViewModel by activityViewModels()

    override fun initStartView() {
        super.initStartView()
        //배경 검은색
        (activity as MainActivity).binding.toolbar.setBackgroundColor(Color.BLACK)
        (activity as MainActivity).binding.btnBack.setImageResource(R.drawable.btn_back_white)
        (activity as MainActivity).binding.backTitle.setTextColor(Color.WHITE)

        (activity as MainActivity).binding.backTitle.text="추천"
        (activity as MainActivity).ShowBackandTitle()
        (activity as MainActivity).binding.navigationMain.visibility=View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.genreMusical.setOnClickListener {
            recommandViewModel.setGenre(0)
            (activity as MainActivity).addFragment(SelectDayandPlaceFragment())
        }
        binding.genrePlay.setOnClickListener {
            recommandViewModel.setGenre(1)
            (activity as MainActivity).addFragment(SelectDayandPlaceFragment())
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()


    }
}