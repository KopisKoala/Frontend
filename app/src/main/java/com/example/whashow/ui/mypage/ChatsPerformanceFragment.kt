package com.example.whashow.ui.mypage

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentChatsPerformanceBinding


class ChatsPerformanceFragment : BaseFragment<FragmentChatsPerformanceBinding>(R.layout.fragment_chats_performance) {
    private lateinit var ChatperformanceListAdapter: ChatPerformanceAdapter

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE

    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
        val chatperformanceList = arrayListOf(
            ChatPerformance("위키드"),
            ChatPerformance("데스노트"),
            ChatPerformance("웃는 남자"),
            ChatPerformance("시카고")
        )
        ChatperformanceListAdapter = ChatPerformanceAdapter(chatperformanceList)
        binding.rcChatperformance.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = ChatperformanceListAdapter
        }

    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE

    }

}