package com.example.shoesshop.features.admin.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.databinding.FragmentOrdersManagerBinding

class OrdersManagerFragment : BaseFragment<FragmentOrdersManagerBinding>() {
    override fun initAction() {

    }

    override fun initView() {
    }

    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOrdersManagerBinding
        get() = FragmentOrdersManagerBinding::inflate

    override fun onViewCreated() {
    }
}