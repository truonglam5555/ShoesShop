package com.example.shoesshop.features.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.databinding.FragmentBannerBinding
import com.example.shoesshop.model.Banner

class BannerFragment : BaseFragment<FragmentBannerBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBannerBinding
        get() = FragmentBannerBinding::inflate

    override fun onViewCreated() {
        val banner = arguments?.get("obj_banner") as? Banner
        if (banner != null) {
            binding.banner.setImageResource(banner.idResource)
        }
    }

    override fun initAction() {
    }

    override fun initView() {
    }
}