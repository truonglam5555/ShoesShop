package com.example.shoesshop.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.shoesshop.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseDialogFragment<V : ViewBinding> : DialogFragment(), View.OnClickListener, BaseFunc {

    lateinit var binding: V

    abstract val _binding: (LayoutInflater, ViewGroup?, Boolean) -> V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme_transparent)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = _binding(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
        initView()
        initAction()
    }

    abstract fun onViewCreated()

    override fun onClick(view: View) {
        view.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
        clickViews(view)
    }

    protected open fun clickViews(view: View) {
        // do nothing
    }
}
