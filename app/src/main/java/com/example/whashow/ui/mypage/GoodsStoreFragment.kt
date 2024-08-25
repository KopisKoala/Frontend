package com.example.whashow.ui.mypage

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.Goods
import com.example.whashow.data.GoodsX
import com.example.whashow.data.Partner
import com.example.whashow.databinding.FragmentGoodsStoreBinding
import com.example.whashow.databinding.FragmentHomeBinding
import com.example.whashow.login.LocalDataSource
import com.example.whashow.ui.pairing.Actor
import com.example.whashow.ui.pairing.ActorAdapterGrid
import com.example.whashow.ui.pairing.Hashtag
import com.example.whashow.ui.pairing.RecentAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoodsStoreFragment : BaseFragment<FragmentGoodsStoreBinding>(R.layout.fragment_goods_store) {

    private lateinit var goodsListManager: GridLayoutManager
    private lateinit var goodsListAdapter: GoodsAdapter
    override fun initStartView() {
        super.initStartView()
    }

    override fun initDataBinding() {
        super.initDataBinding()

        val goodsList= arrayListOf(
            GoodsX(1,"http://www.kopis.or.kr/upload/pfmPoster/PF_PF241368_240520_135016.jpg","위키드", 14500,"친환경 패키지 굿즈 클립서비스"),
            GoodsX(2,"http://www.kopis.or.kr/upload/pfmPoster/PF_PF241368_240520_135016.jpg","영웅",14500,"머그컵, USB, 카드스티커"),
            GoodsX(3,"http://www.kopis.or.kr/upload/pfmPoster/PF_PF241368_240520_135016.jpg","안중근",10000, "친환경 패키지 굿즈 클립서비스"),
            GoodsX(4,"http://www.kopis.or.kr/upload/pfmPoster/PF_PF241368_240520_135016.jpg", "한여름 밤의 꿈",22000,"머그컵, USB, 카드스티커")
        )
        // 선택되지 않은 경우
        val Call: Call<Goods> =
            ApiManager.mypageService.getGoodsList(
                "Bearer " + LocalDataSource.getAccessToken()
            )
        // 비동기적으로 요청 수행
        Call.enqueue(object : Callback<Goods> {
            override fun onResponse(
                call: Call<Goods>,
                response: Response<Goods>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()?.result?.goodsList as ArrayList<GoodsX>
                    goodsListAdapter.list=list
                    goodsListAdapter.notifyDataSetChanged()
                    Log.d("굿즈 서버", response.body()?.result.toString())

                } else {
                    // 서버에서 오류 응답을 받은 경우 처리
                    Log.d("굿즈 서버", response.toString())
                }

            }

            override fun onFailure(call: Call<Goods>, t: Throwable) {
                // 통신 실패 처리
                Log.d("굿즈 서버", t.message.toString())
            }

        })


        goodsListManager = GridLayoutManager(requireContext(), 2)
        goodsListAdapter = GoodsAdapter(goodsList)

        val goodesRecyclerList = binding.rv.apply {
            setHasFixedSize(true)
            layoutManager = goodsListManager
            adapter = goodsListAdapter
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
    }
}