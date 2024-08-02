package com.example.whashow.ui.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentHomeBinding
import com.example.whashow.ui.home.Adapter.BannerAdapter
import com.example.whashow.ui.home.Adapter.PopularAdapter
import com.example.whashow.ui.home.Adapter.RecommandHomeAdapter
import com.google.gson.Gson

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var popularListAdapter: PopularAdapter
    private lateinit var recommandListAdapter: RecommandHomeAdapter
    private lateinit var bannerListAdapter: BannerAdapter

    val popularList = arrayListOf(
        Popular(R.drawable.img_recommand_post, "2020 신년 콘서트 VOICE OF THE MAS..", "인천문화예술회관 대공연장 (1.2km)", 3.5F, "(434)"),
        Popular(R.drawable.img_recommand_post2, "예술 회복 지원 사업 뮤지컬 한여름밤의 꿈", "경기아트센터 소극장 (2.3km)", 2F, "(12)"),
        Popular(R.drawable.img_recommand_post3, "서초 문화재단 SSUM 타는 콘서트", "경기아트센터 소극장 (2.3km)", 5F, "(35)"),
        Popular(R.drawable.img_recommand_post4, "대한민국 대표 창작 뮤지컬 빨래", "동양예술극장 1관", 4.5F, "(125)"),
        Popular(R.drawable.img_recommand_post, "2020 신년 콘서트 VOICE OF THE MAS..", "인천문화예술회관 대공연장 (1.2km)", 3.5F, "(434)"),
        Popular(R.drawable.img_recommand_post2, "예술 회복 지원 사업 뮤지컬 한여름밤의 꿈", "경기아트센터 소극장 (2.3km)", 2F, "(12)"),
        Popular(R.drawable.img_recommand_post3, "서초 문화재단 SSUM 타는 콘서트", "경기아트센터 소극장 (2.3km)", 5F, "(35)"),
        Popular(R.drawable.img_recommand_post4, "대한민국 대표 창작 뮤지컬 빨래", "동양예술극장 1관", 4.5F, "(125)")
    )

    val recommandList = arrayListOf(
        Recommand(R.drawable.img_recommand_post4, "대한민국 대표 창작 뮤지컬 빨래", "동양예술극장 1관", 4.5F, "(125)"),
        Recommand(R.drawable.img_recommand_post3, "서초 문화재단 SSUM 타는 콘서트", "경기아트센터 소극장 (2.3km)", 5F, "(35)"),
        Recommand(R.drawable.img_recommand_post, "2020 신년 콘서트 VOICE OF THE MAS..", "인천문화예술회관 대공연장 (1.2km)", 3.5F, "(434)"),
        Recommand(R.drawable.img_recommand_post2, "예술 회복 지원 사업 뮤지컬 한여름밤의 꿈", "경기아트센터 소극장 (2.3km)", 2F, "(12)"),
        Recommand(R.drawable.img_recommand_post3, "서초 문화재단 SSUM 타는 콘서트", "경기아트센터 소극장 (2.3km)", 5F, "(35)"),
        Recommand(R.drawable.img_recommand_post4, "대한민국 대표 창작 뮤지컬 빨래", "동양예술극장 1관", 4.5F, "(125)"),
        Recommand(R.drawable.img_recommand_post4, "대한민국 대표 창작 뮤지컬 빨래", "동양예술극장 1관", 4.5F, "(125)"),
        Recommand(R.drawable.img_recommand_post, "2020 신년 콘서트 VOICE OF THE MAS..", "인천문화예술회관 대공연장 (1.2km)", 3.5F, "(434)"),
        Recommand(R.drawable.img_recommand_post2, "예술 회복 지원 사업 뮤지컬 한여름밤의 꿈", "경기아트센터 소극장 (2.3km)", 2F, "(12)"),
        Recommand(R.drawable.img_recommand_post3, "서초 문화재단 SSUM 타는 콘서트", "경기아트센터 소극장 (2.3km)", 5F, "(35)"),
        Recommand(R.drawable.img_recommand_post4, "대한민국 대표 창작 뮤지컬 빨래", "동양예술극장 1관", 4.5F, "(125)")
    )

    val bannerList = arrayListOf(
        Banner(R.drawable.img_banner),
        Banner(R.drawable.img_recommand_post),
        Banner(R.drawable.img_detail),
        Banner(R.drawable.img_banner),
        Banner(R.drawable.img_banner)
    )

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowLogoAndSearch()
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.VISIBLE

        // 배너 어댑터 초기화
        bannerListAdapter = BannerAdapter(bannerList)
        bannerListAdapter.setMyItemClickListener(object : BannerAdapter.MyItemClickListener {
            override fun onItemClick(banner: Banner) {
                val fragment = PerformanceDetailFragment().apply {
                    arguments = Bundle().apply {
                        val gson = Gson()
                        val bannerJson = gson.toJson(banner)
                        putString("banner", bannerJson)
                    }
                }
                (activity as MainActivity).addFragment(fragment)
            }
        })
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.VISIBLE

        popularListAdapter = PopularAdapter(popularList)
        recommandListAdapter = RecommandHomeAdapter(recommandList)

        binding.viewpagerBanner.apply {
            adapter = bannerListAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            })
        }

        binding.rcPopular.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularListAdapter
        }

        binding.rcCommand.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = recommandListAdapter
        }

        binding.btnEtiquette.setOnClickListener {
            (activity as? MainActivity)?.addFragment(EtiquetteFragment())
        }
    }
}
