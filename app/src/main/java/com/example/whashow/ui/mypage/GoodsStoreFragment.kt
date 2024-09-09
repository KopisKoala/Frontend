package com.example.whashow.ui.mypage

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.Goods
import com.example.whashow.data.GoodsResultDto
import com.example.whashow.databinding.FragmentGoodsStoreBinding
import com.example.whashow.login.LocalDataSource
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
                    val list = response.body()?.result?.goodsList as ArrayList<GoodsResultDto>
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
        goodsListAdapter = GoodsAdapter(arrayListOf())

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