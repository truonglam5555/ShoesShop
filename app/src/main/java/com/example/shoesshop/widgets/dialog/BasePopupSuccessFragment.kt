package com.example.shoesshop.widgets.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseDialogFragment
import com.example.shoesshop.constants.DataShared
import com.example.shoesshop.databinding.FragmentBasePopupBinding
import com.example.shoesshop.databinding.FragmentBasePopupSuccessBinding

class BasePopupSuccessFragment(private val title: String = "", private val btText: String = "", private val icon: Int = R.drawable.img_success) : BaseDialogFragment<FragmentBasePopupSuccessBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBasePopupSuccessBinding
        get() = FragmentBasePopupSuccessBinding::inflate


    var onCallback: (() -> Unit)? = null

    override fun onViewCreated() {

    }

    override fun initView() {
        binding.tvTitle.text = title
        binding.image.setImageResource(icon)
        binding.btSubmit.text = btText
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            DataShared.getWidth()!! - resources.getDimension(R.dimen.spacing_20).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun initAction() {
        binding.btSubmit.setOnClickListener {
            onCallback?.invoke()
            dismiss()
        }
    }

}