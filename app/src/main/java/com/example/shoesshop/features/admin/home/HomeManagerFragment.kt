package com.example.shoesshop.features.admin.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.FragmentHomeManagerBinding

class HomeManagerFragment : BaseFragment<FragmentHomeManagerBinding>() {
    override fun initAction() {

    }

    override fun initView() {
    }

    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeManagerBinding
        get() = FragmentHomeManagerBinding::inflate

    override fun onViewCreated() {
        this.binding.tvCountAcount.text = FetchDataFirebase.share.listUser.size.toString()
        this.binding.tvCountOders.text = FetchDataFirebase.share.listBillOder.size.toString()
        this.binding.tvCountOdersComfirm.text = FetchDataFirebase.share.listBillOder.size.toString()
        this.binding.tvCountOderscaneled.text = FetchDataFirebase.share.listBillOder.size.toString()
    }
}