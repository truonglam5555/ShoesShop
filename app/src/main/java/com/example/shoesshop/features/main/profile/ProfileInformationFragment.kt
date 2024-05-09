package com.example.shoesshop.features.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.FragmentProfileInfomationBinding
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.ViewUtils.showView


class ProfileInformationFragment : BaseFragment<FragmentProfileInfomationBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileInfomationBinding
        get() = FragmentProfileInfomationBinding::inflate

    override fun onViewCreated() {
    }

    override fun initAction() {
        binding.layoutHeader.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.layoutHeader.tvEnd.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initView() {
        binding.layoutHeader.tvTitle.text = getString(R.string.text_profile)
        binding.layoutHeader.imgBack.setImage(R.drawable.ic_back_auth)
        binding.layoutHeader.tvEnd.showView()



//        First name
        binding.layoutFirstName.tvTitle.text = getString(R.string.text_first_name)
        binding.layoutFirstName.tvName.text = "Emmanuel"
//        Last Name
        binding.layoutLastName.tvTitle.text = getString(R.string.text_last_name)
        binding.layoutLastName.tvName.text = "Oyiboke"
//        Location
        binding.layoutLocation.tvTitle.text = getString(R.string.text_location)
        binding.layoutLocation.tvName.text = "Nigeria"
//        Phone Number
        binding.layoutPhone.tvTitle.text = getString(R.string.text_mobile_number)
        binding.layoutPhone.tvName.text = "+234 ^ 811-732-5298"
    }

}