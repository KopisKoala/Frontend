package com.example.whashow.ui.pairing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentGenreBinding
import com.example.whashow.databinding.FragmentPairingBinding
import com.example.whashow.ui.recommand.Tag
import com.example.whashow.ui.recommand.TagAdapter

class PairingFragment : BaseFragment<FragmentPairingBinding>(R.layout.fragment_pairing) {

    private lateinit var recentListAdapter: RecentAdapter
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.mainTitle.text="페어링"
        (activity as MainActivity).ShowTitle()
        (activity as MainActivity).binding.navigationMain.visibility=View.VISIBLE
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.search.isSubmitButtonEnabled = true
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query=="손승연"){
                    (activity as MainActivity).addFragment(ActorSearchFragment())
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })

        val recentList = arrayListOf(
            RecentPairing(R.drawable.img_actor1,R.drawable.img_actor2,"옥주현 정선아", "위키드", arrayListOf(
                Hashtag("웅장한"), Hashtag("멋있는")
            ) ),
            RecentPairing(R.drawable.img_actor3,R.drawable.img_actor2,"손승연 정선아", "위키드", arrayListOf(Hashtag("환상의 하모니")) )
        )

        recentListAdapter = RecentAdapter(recentList)
        binding.recentRv.adapter = recentListAdapter
        binding.recentRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recentListAdapter.setMyItemClickListener(object: RecentAdapter.MyItemClickListener{
            override fun onDeleteClick(position: Int) {
                recentListAdapter.removeItem(position)
                recentListAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}