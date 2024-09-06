package com.example.whashow.ui.home

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.DetailActor
import com.example.whashow.databinding.FragmentCastingBinding
import com.example.whashow.ui.home.homeAdapter.CastingAdapter
class CastingFragment : BaseFragment<FragmentCastingBinding>(R.layout.fragment_casting) {

    private lateinit var castingAdapter: CastingAdapter
    private lateinit var castingListManager: LinearLayoutManager
    private var perfId: Int? = null
    private val viewModel: PerformanceDetailViewModel by viewModels()

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
        perfId = arguments?.getInt("perfId")

        setupRecyclerView(emptyList())
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE

        perfId?.let { id ->
            viewModel.fetchPerformanceData(performanceId = id)
        }


        viewModel.actorList.observe(viewLifecycleOwner) { actorList ->
            Log.d("CastingFragment", "Actor list updated: $actorList")
            castingAdapter.updateList(ArrayList(actorList))
        }


    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }

    private fun setupRecyclerView(actorList: List<DetailActor>) {
        castingListManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        castingAdapter = CastingAdapter(ArrayList(actorList))
        binding.rvCasting.apply {
            setHasFixedSize(true)
            layoutManager = castingListManager
            adapter = castingAdapter

            castingAdapter.setMyItemClickListener(object : CastingAdapter.MyItemClickListener{
                override fun onLikeClick(actor: DetailActor, isLike: String) {

                    val newIsFavorite = if (isLike == "Y") "N" else "Y"
                    actor.isFavoriteActor = newIsFavorite

                    if (newIsFavorite == "Y") {
                        viewModel.postLikeActor(actor.id)
                    } else {
                        viewModel.postDisLikeActor(actor.id)
                    }
                    castingAdapter.notifyDataSetChanged()
                }

                override fun onDeleteClick(position: Int) {
                    //
                }
            })
        }
    }
}

