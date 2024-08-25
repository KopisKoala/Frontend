package com.example.whashow.ui.mypage

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.FavoriteActorResDto
import com.example.whashow.data.Goods
import com.example.whashow.data.GoodsX
import com.example.whashow.data.MyActor
import com.example.whashow.databinding.FragmentMyActorBinding
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyActorFragment : BaseFragment<FragmentMyActorBinding>(R.layout.fragment_my_actor) {

    private lateinit var actorListManager: GridLayoutManager
    private lateinit var actorListAdapter: MyActorAdapter
    override fun initStartView() {
        super.initStartView()

    }

    override fun initDataBinding() {
        super.initDataBinding()

        val actorList= arrayListOf(
            FavoriteActorResDto(1,"김영웅","http://www.kopis.or.kr/upload/pfmPoster/PF_PF241368_240520_135016.jpg", 1),
            FavoriteActorResDto(2,"정선아","http://www.kopis.or.kr/upload/pfmPoster/PF_PF241368_240520_135016.jpg",1),
            FavoriteActorResDto(3,"김준형","http://www.kopis.or.kr/upload/pfmPoster/PF_PF241368_240520_135016.jpg",1),
            FavoriteActorResDto(4,"양준모","http://www.kopis.or.kr/upload/pfmPoster/PF_PF241368_240520_135016.jpg",1)
        )
        /*// 선택되지 않은 경우
        val Call: Call<MyActor> =
            ApiManager.mypageService.getMyActorList(
                "Bearer " + LocalDataSource.getAccessToken(),0,3
            )
        // 비동기적으로 요청 수행
        Call.enqueue(object : Callback<MyActor> {
            override fun onResponse(
                call: Call<MyActor>,
                response: Response<MyActor>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()?.result?.favoriteActorResDtoList as ArrayList<FavoriteActorResDto>
                    actorListAdapter.list=list
                    actorListAdapter.notifyDataSetChanged()
                    Log.d("내 배우 서버", response.body()?.result.toString())

                } else {
                    // 서버에서 오류 응답을 받은 경우 처리
                    Log.d("내 배우 서버", response.toString())
                }

            }

            override fun onFailure(call: Call<MyActor>, t: Throwable) {
                // 통신 실패 처리
                Log.d("내 배우 서버", t.message.toString())
            }

        })*/

        actorListManager = GridLayoutManager(requireContext(), 3)
        actorListAdapter = MyActorAdapter(actorList)

        binding.editActor.setOnClickListener {
            binding.editActor.visibility=View.GONE
            binding.finishEdit.visibility=View.VISIBLE
            actorListAdapter.editClick=true
            actorListAdapter.notifyDataSetChanged()

        }
        binding.finishEdit.setOnClickListener {
            binding.finishEdit.visibility=View.GONE
            binding.editActor.visibility=View.VISIBLE
            actorListAdapter.editClick=false
            actorListAdapter.notifyDataSetChanged()

        }

        val actorRecyclerList = binding.rv.apply {
            setHasFixedSize(true)
            layoutManager = actorListManager
            adapter = actorListAdapter
        }
    }


        override fun initAfterBinding() {
        super.initAfterBinding()

    }
}