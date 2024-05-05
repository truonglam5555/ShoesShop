package com.example.shoesshop.features.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.databinding.FragmentShoesBinding

class NewShoesFragment : BaseFragment<FragmentShoesBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentShoesBinding
        get() = FragmentShoesBinding::inflate

    override fun onViewCreated() {
        setUpReyclerView()
    }

    override fun initAction() {
    }

    override fun initView() {
    }

    private fun setUpReyclerView() {

    }
}