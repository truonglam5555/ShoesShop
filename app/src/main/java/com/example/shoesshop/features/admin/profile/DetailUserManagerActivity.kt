package com.example.shoesshop.features.admin.profile

import com.example.shoesshop.R
import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.databinding.ActivityDetailUserManagerBinding

class DetailUserManagerActivity :
    BaseActivity<ActivityDetailUserManagerBinding>(ActivityDetailUserManagerBinding::inflate) {
    override fun onCreateView() {
        super.onCreateView()
        binding.layoutHeader.tvTitle.text = getString(R.string.tv_edit_user)
    }
}