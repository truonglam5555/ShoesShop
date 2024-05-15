package com.example.shoesshop.features.main.profile

import android.content.Intent
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.ActivityProfileBinding
import com.example.shoesshop.features.main.activity.HomeActivity
import com.example.shoesshop.utils.ImageUtils.setImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>(ActivityProfileBinding::inflate){

    override fun onCreateView() {
        super.onCreateView()
        binding.btSave.setOnClickListener {
//            it.navigateTo(R.id.action_profileFragment_to_profileInformationFragment)
        }
        binding.layoutHeader.imgBack.clickWithAnimationDebounce {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        initView()
    }

    fun initView() {
        binding.layoutHeader.imgBack.setImage(R.drawable.ic_back_auth)

        val user = FetchDataFirebase.share.getCurrentUser()
        binding.layoutHeader.tvTitle.text = getString(R.string.text_profile)

        binding.layoutYourName.tvTitle.text = getString(R.string.text_your_name)
        binding.layoutYourName.edtName.setText(user.fullName)

        binding.layoutEmail.tvTitle.text = getString(R.string.text_email_address_title)
        binding.layoutEmail.edtEmail.setText(user.email)

        binding.layoutPassword.tvTitle.text = getString(R.string.text_password_title)
        binding.layoutPassword.edtPassword.setText(user.pass)
    }
}
