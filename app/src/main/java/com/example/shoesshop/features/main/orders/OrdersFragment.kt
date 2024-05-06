package com.example.shoesshop.features.main.orders

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.databinding.FragmentOrdersBinding
import com.example.shoesshop.features.main.activity.HomeActivity

class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOrdersBinding
        get() = FragmentOrdersBinding::inflate

    override fun onViewCreated() {
        binding.layoutHeader.imgBack.clickWithAnimationDebounce {
            startActivity(Intent(requireContext(), HomeActivity::class.java))

        }
        binding.layoutHeader.tvTitle.text = "Your Orders"
    }

    override fun initAction() {
        ///
    }

    override fun initView() {
        //setUpList
    }
}