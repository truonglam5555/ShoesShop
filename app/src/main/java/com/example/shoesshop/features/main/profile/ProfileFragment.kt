package com.example.shoesshop.features.main.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.databinding.FragmentProfileBinding
import com.example.shoesshop.features.main.activity.HomeActivity
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.ViewUtils.navigateTo

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    override fun onViewCreated() {

    }

    override fun initAction() {
        binding.btSave.setOnClickListener {
//            it.navigateTo(R.id.action_profileFragment_to_profileInformationFragment)
        }
        binding.layoutHeader.imgBack.clickWithAnimationDebounce {
            startActivity(Intent(requireContext(), HomeActivity::class.java))
        }
    }

    override fun initView() {
        binding.layoutHeader.imgBack.setImage(R.drawable.ic_back_auth)
        binding.layoutHeader.tvTitle.text = getString(R.string.text_profile)

        binding.layoutYourName.tvTitle.text = getString(R.string.text_your_name)
        binding.layoutYourName.edtName.setText("EMMANUEL OYIBOKE")

        binding.layoutEmail.tvTitle.text = getString(R.string.text_email_address_title)
        binding.layoutEmail.edtEmail.setText("emmanueloyiboke@gmail.com")

        binding.layoutPassword.tvTitle.text = getString(R.string.text_password_title)
    }
}