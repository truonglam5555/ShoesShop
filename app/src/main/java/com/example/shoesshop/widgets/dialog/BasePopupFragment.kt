package com.example.shoesshop.widgets.dialog

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseDialogFragment
import com.example.shoesshop.constants.DataShared
import com.example.shoesshop.databinding.FragmentBasePopupBinding

class BasePopupFragment(private val title: String = "", private val content: String = "", private val icon: Int = R.drawable.ic_verify_sms) : BaseDialogFragment<FragmentBasePopupBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBasePopupBinding
        get() = FragmentBasePopupBinding::inflate

    var onCallback: (() -> Unit)? = null
    override fun onViewCreated() {

    }

    override fun initAction() {

    }

    override fun initView() {
        binding.tvTitle.text = title
        binding.tvContent.text = content
        binding.image.setImageResource(icon)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            DataShared.getWidth()!! - resources.getDimension(R.dimen.spacing_20).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onCallback?.invoke()
    }
}