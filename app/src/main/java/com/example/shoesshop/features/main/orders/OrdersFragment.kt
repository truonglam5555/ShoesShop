package com.example.shoesshop.features.main.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.databinding.FragmentOrdersBinding

class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOrdersBinding
        get() = FragmentOrdersBinding::inflate

    override fun onViewCreated() {

    }

    override fun initAction() {
        TODO("Not yet implemented")
    }

    override fun initView() {

    }
}