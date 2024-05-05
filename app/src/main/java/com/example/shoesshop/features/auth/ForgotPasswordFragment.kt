package com.example.shoesshop.features.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.databinding.FragmentForgotPasswordBinding
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.ViewUtils.hideView
import com.example.shoesshop.utils.ViewUtils.navigateTo
import com.example.shoesshop.widgets.dialog.BasePopupFragment

class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>() {

    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForgotPasswordBinding
        get() = FragmentForgotPasswordBinding::inflate

    private lateinit var dialogCheckEmail: BasePopupFragment

    override fun onViewCreated() {
        dialogCheckEmail = BasePopupFragment(title = getString(R.string.text_check_your_email), content = getString(R.string.text_description_forgot_password))
    }

    override fun initAction() {
        binding.layoutHeader.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btResetPassword.setOnClickListener {
            dialogCheckEmail.show(childFragmentManager, "")
        }

        dialogCheckEmail.onCallback = {
            requireView().navigateTo(R.id.action_forgotPasswordFragment_to_OTPFragment)
        }
    }

    override fun initView() {
        binding.layoutHeader.imgBack.setImage(R.drawable.ic_back_auth)
        binding.layoutTitleAuthScreen.tvHeading.text = getString(R.string.text_forgot_password)
        binding.layoutTitleAuthScreen.tvDescription.text = getString(R.string.text_description_forgot_password)
        binding.layoutName.tvTitle.hideView()
    }


}