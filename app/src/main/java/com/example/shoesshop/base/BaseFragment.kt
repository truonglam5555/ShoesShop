package com.example.shoesshop.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ViewSwitcher.ViewFactory
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.shoesshop.R
import com.example.shoesshop.features.main.cart.view_model.ProductCartViewModel
import com.example.shoesshop.features.main.home.view_model.HomeViewModel
import com.example.shoesshop.features.main.home.view_model.SearchViewModel
import com.example.shoesshop.features.main.notification.model.Notification
import com.example.shoesshop.features.main.notification.view_model.NotificationViewModel

interface BaseFunc {
    fun initAction()
    fun initView()
}
abstract class BaseFragment<V : ViewBinding> : Fragment(), View.OnClickListener, BaseFunc {

    lateinit var binding: V

    abstract val _binding: (LayoutInflater, ViewGroup?, Boolean) -> V

    lateinit var homeViewModel: HomeViewModel
    lateinit var productCartViewModel: ProductCartViewModel
    lateinit var notificationViewModel: NotificationViewModel
    lateinit var searchViewModel: SearchViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        productCartViewModel = ViewModelProvider(requireActivity())[ProductCartViewModel::class.java]
        notificationViewModel = ViewModelProvider(requireActivity())[NotificationViewModel::class.java]
        searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
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