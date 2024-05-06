package com.example.shoesshop.features.auth

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.databinding.FragmentOTPBinding
import com.example.shoesshop.features.main.activity.HomeActivity
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.TextInputUtils.countdownTimer
import com.otpview.OTPTextView

class OTPFragment : BaseFragment<FragmentOTPBinding>() {

    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOTPBinding
        get() = FragmentOTPBinding::inflate
    private lateinit var otpTextView: OTPTextView

    override fun onViewCreated() {

        otpTextView = binding.otpView
        otpTextView.requestFocusOTP()
        sendOTP()
    }

    override fun initAction() {
        binding.layoutHeader.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btVerify.setOnClickListener {
            if (otpTextView.otp == "1234"){
                otpTextView.showSuccess()
                requireActivity().finish()
                requireActivity().startActivity(Intent(requireActivity(), HomeActivity::class.java))
            }else{
                otpTextView.showError()
            }
        }

        binding.tvCountDown.setOnClickListener {
            sendOTP()
        }
    }

    override fun initView() {
        binding.layoutHeader.imgBack.setImage(R.drawable.ic_back_auth)
        binding.layoutTitleAuthScreen.tvHeading.text = getString(R.string.text_otp_verification)
        binding.layoutTitleAuthScreen.tvDescription.text = getString(R.string.text_description_otp)
    }

    private fun sendOTP(){
        binding.tvCountDown.countdownTimer(getString(R.string.text_resend_otp))
    }

}