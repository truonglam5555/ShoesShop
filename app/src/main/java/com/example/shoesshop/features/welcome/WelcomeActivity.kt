package com.example.shoesshop.features.welcome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.window.SplashScreen
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.databinding.ActivityWelcomeBinding
import com.example.shoesshop.features.auth.AuthActivity
import com.example.shoesshop.features.splash.SpashActivity
import com.example.shoesshop.features.welcome.adapter.OnboardingViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>(ActivityWelcomeBinding::inflate) {

    private lateinit var mViewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewPager = binding.viewPager
        mViewPager.adapter = OnboardingViewPagerAdapter(this, this)
        TabLayoutMediator(binding.pageIndicator, mViewPager) { _, _ -> }.attach()

        binding.btGetStarted.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem <= binding.viewPager.childCount) {
                binding.viewPager.setCurrentItem(currentItem + 1, true)
            }else{
                finish()
                val intent =
                    Intent(applicationContext, AuthActivity::class.java)
                startActivity(intent)
//                Animatoo.animateSlideLeft(this)
            }
        }
        binding.pageIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
                if (tab.position == 0){
                    binding.btGetStarted.text = getString(R.string.text_get_started)
                }else{
                    binding.btGetStarted.text = getString(R.string.text_next)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }
        })
    }

    private fun getItem(): Int {
        return mViewPager.currentItem
    }
}