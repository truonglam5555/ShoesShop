package com.example.shoesshop.features.admin.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.databinding.FragmentHomeManagerBinding

class HomeManagerFragment : BaseFragment<FragmentHomeManagerBinding>() {
    override fun initAction() {

    }

    override fun initView() {
    }

    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeManagerBinding
        get() = FragmentHomeManagerBinding::inflate

    override fun onViewCreated() {
    }
}