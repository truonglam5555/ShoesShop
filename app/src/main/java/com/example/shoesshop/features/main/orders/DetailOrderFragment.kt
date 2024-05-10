package com.example.shoesshop.features.main.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.FragmentDetailOrderBinding
import com.example.shoesshop.features.main.cart.adapter.ProductCartAdapter
import com.example.shoesshop.model.BillOder
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.RecyclerViewUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailOrderFragment : BaseFragment<FragmentDetailOrderBinding>() {
    private lateinit var productCartAdapter: ProductCartAdapter


    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailOrderBinding
        get() = FragmentDetailOrderBinding::inflate

    override fun onViewCreated() {
        initAdapter()
        binding.layoutHeader.tvTitle.text = getString(R.string.text_order)
        binding.layoutOrderDetail.layoutTitleMenu2.visibility = View.GONE
        binding.layoutHeader.imgBack.clickWithAnimationDebounce { }
    }

    override fun initAction() {
    }

    override fun initView() {
    }
    private fun initAdapter() {
        productCartAdapter = ProductCartAdapter()
        RecyclerViewUtils.initAdapter(
            mAdapter = productCartAdapter,
            rev = binding.layoutOrderDetail.revCommon
        )
    }
}