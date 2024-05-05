package com.example.shoesshop.features.main.home.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shoesshop.features.main.home.BannerFragment
import com.example.shoesshop.model.Banner

class AdapterShowBanner(
    fragmentActivity: FragmentActivity,
    private val bannerList: List<Banner>
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return bannerList.size
    }

    override fun createFragment(position: Int): Fragment {
        val banner = bannerList[position]
        return BannerFragment().apply {
            arguments = Bundle().apply {
                putSerializable("obj_banner", banner)
            }
        }
    }
}