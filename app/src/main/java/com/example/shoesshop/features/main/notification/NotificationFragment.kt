package com.example.shoesshop.features.main.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.databinding.FragmentNotificationBinding
import com.example.shoesshop.features.main.notification.adapter.NotificationAdapter
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.RecyclerViewUtils
import com.example.shoesshop.utils.ViewUtils.hideView
import com.example.shoesshop.utils.ViewUtils.showView

class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotificationBinding
        get() = FragmentNotificationBinding::inflate

    private lateinit var recentNotificationAdapter: NotificationAdapter
    private lateinit var lastNotificationAdapter: NotificationAdapter

    override fun onViewCreated() {
        initAdapter()
        click()
    }

    private fun initAdapter() {
        recentNotificationAdapter = NotificationAdapter()
        RecyclerViewUtils.initAdapter(
            mAdapter = recentNotificationAdapter,
            rev = binding.layoutRecent.revCommon
        )
        recentNotificationAdapter.submitList(notificationViewModel.listNotification)
        recentNotificationAdapter.notifyDataSetChanged()

        lastNotificationAdapter = NotificationAdapter()
        RecyclerViewUtils.initAdapter(
            mAdapter = lastNotificationAdapter,
            rev = binding.layoutYesterday.revCommon
        )
        lastNotificationAdapter.submitList(notificationViewModel.listNotification)
        lastNotificationAdapter.notifyDataSetChanged()
    }

    private fun click() {

    }

    override fun initAction() {
        binding.layoutHeader.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initView() {

        binding.layoutHeader.tvTitle.text = getString(R.string.text_notifications)
        binding.layoutHeader.icControl.showView()
        binding.layoutHeader.icControl.setImage(R.drawable.ic_delete_circle)


        notificationViewModel.setNotification()
//        Recent
        binding.layoutRecent.tvNext.hideView()
        binding.layoutRecent.tvTitle.text = getString(R.string.text_recent)
//        Last
        binding.layoutYesterday.tvNext.hideView()
        binding.layoutYesterday.tvTitle.text = getString(R.string.text_yesterday)
    }
}