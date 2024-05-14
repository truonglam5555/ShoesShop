package com.example.shoesshop.features.main.orders

import android.view.View
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.databinding.ActivityDetailOrderBinding
import com.example.shoesshop.features.main.cart.adapter.ProductCartAdapter
import com.example.shoesshop.utils.RecyclerViewUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class DetailOrderActivity :
    BaseActivity<ActivityDetailOrderBinding>(ActivityDetailOrderBinding::inflate) {
    private lateinit var productCartAdapter: ProductCartAdapter
    override fun onCreateView() {
        super.onCreateView()
        initAdapter()
        getData()
        binding.layoutHeader.tvTitle.text = getString(R.string.text_order)
        binding.layoutOrderDetail.layoutTitleMenu2.visibility = View.GONE
        binding.layoutHeader.imgBack.clickWithAnimationDebounce {

        }
    }

    private fun getData() {
        val data = intent.getSerializableExtra("order") as Order
        //set data here

    }

    private fun initAdapter() {
        productCartAdapter = ProductCartAdapter()
        RecyclerViewUtils.initAdapter(
            mAdapter = productCartAdapter,
            rev = binding.layoutOrderDetail.revCommon
        )
    }
}
