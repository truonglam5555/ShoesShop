package com.example.shoesshop.features.main.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingBinding
        get() = FragmentSettingBinding::inflate

    override fun onViewCreated() {

    }

    override fun initAction() {
        //TODO("Not yet implemented")
    }

    override fun initView() {

    }
}