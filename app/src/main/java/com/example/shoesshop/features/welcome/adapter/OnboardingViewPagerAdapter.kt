package com.example.shoesshop.features.welcome.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shoesshop.R
import com.example.shoesshop.features.welcome.WelcomeFragment


class OnboardingViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val context: Context
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WelcomeFragment.newInstance(
                R.drawable.img_welcome_1,
            )

            1 -> WelcomeFragment.newInstance(
                R.drawable.img_welcome_2,
            )

            2 -> WelcomeFragment.newInstance(
                R.drawable.img_welcome_3
            )

            else -> WelcomeFragment.newInstance(
                R.drawable.img_welcome_1,
            )
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}