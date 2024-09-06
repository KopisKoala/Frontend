package com.example.whashow.ui.pairing

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentActorSearchBinding
import com.example.whashow.ui.pairing.pairingAdapter.ActorAdapterGrid

class ActorSearchFragment : BaseFragment<FragmentActorSearchBinding>(R.layout.fragment_actor_search) {

    private lateinit var actorListManager: GridLayoutManager
    private lateinit var actorListAdapter: ActorAdapterGrid
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.backTitle.text="페어링 검색"
        (activity as MainActivity).ShowBackandTitle()
        (activity as MainActivity).binding.navigationMain.visibility=View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.search.isSubmitButtonEnabled = true
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })



        actorListManager = GridLayoutManager(requireContext(), 2)
        actorListAdapter = ActorAdapterGrid(arrayListOf())

        binding.actorRv.apply {
            setHasFixedSize(true)
            layoutManager = actorListManager
            adapter = actorListAdapter
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}