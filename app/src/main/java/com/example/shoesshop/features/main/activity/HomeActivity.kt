package com.example.shoesshop.features.main.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.example.shoesshop.NoScrollViewPagerAdapter
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.databinding.ActivityMainBinding
import com.example.shoesshop.features.main.cart.CartFragment
import com.example.shoesshop.features.main.favorite.FavoriteFragment
import com.example.shoesshop.features.main.home.HomeFragment
import com.example.shoesshop.features.main.notification.NotificationFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    NavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val REPLACE_DRAWER = "REPLACE_DRAWER"
    }

    private lateinit var adapterCollection: NoScrollViewPagerAdapter
    private val frmHome by lazy { HomeFragment() }
    private val frmFav by lazy { FavoriteFragment() }
    private val frmCart by lazy { CartFragment() }
    private val frmNotify by lazy { NotificationFragment() }

    override fun onCreateView() {
        super.onCreateView()
        initViews()
    }

    private fun initViews() {
        binding.btOpen.clickWithAnimationDebounce { binding.drawLayout.openDrawer(GravityCompat.START) }
        initViewPager()
        listenerView()
    }

    private fun listenerView() {
        binding.run {
            viewTabHome.clickWithAnimationDebounce {
                if (viewPager.currentItem != 0) viewPager.setCurrentItem(0, true)
                tvTabHome.text = getString(R.string.tv_home)
                tvTab.text = getString(R.string.tv_explore)
                icMessage.setImageResource(R.drawable.ic_message)
            }
            viewTabFavorite.clickWithAnimationDebounce {
                if (viewPager.currentItem != 1) viewPager.setCurrentItem(1, true)
                frmFav.refreshAdepter()
                tvTab.text = getString(R.string.tv_favorite)
                tvTabFav.text = getString(R.string.tv_favorite)
                icMessage.setImageResource(R.drawable.ic_fav_unchecked)
            }
            viewTabCart.clickWithAnimationDebounce {
                if (viewPager.currentItem != 2) viewPager.setCurrentItem(2, true)
                tvTab.text = getString(R.string.tv_my_cart)
                tvTabTabCart.text = getString(R.string.tv_my_cart)
                icMessage.setImageResource(R.drawable.ic_cart)

            }
            viewTabNotifi.clickWithAnimationDebounce {
                if (viewPager.currentItem != 3) viewPager.setCurrentItem(3, true)
                tvTab.text = getString(R.string.tv_notification)
                tvTabNotifi.text = getString(R.string.tv_notification)
                icMessage.setImageResource(R.drawable.ic_notification_unchecked)
            }
        }
    }

    private fun initViewPager() {
        adapterCollection = NoScrollViewPagerAdapter(supportFragmentManager)
        adapterCollection.apply {
            addFragment(frmHome, "Home")
            addFragment(frmFav, "Favorite")
            addFragment(frmCart, "Cart")
            addFragment(frmNotify, "Notification")
        }
        binding.viewPager.offscreenPageLimit = 4
        binding.viewPager.adapter = adapterCollection

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                updateStateTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        binding.navView.setNavigationItemSelectedListener(this@HomeActivity)
    }

    private fun updateStateTab(index: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.run {
                tvTabHome.setTextColor(
                    if (index == 0) getColor(R.color.blue_main) else getColor(
                        R.color.defaultText
                    )
                )
                tvTabFav.setTextColor(
                    if (index == 1) getColor(R.color.blue_main) else getColor(
                        R.color.defaultText
                    )
                )
                tvTabTabCart.setTextColor(
                    if (index == 2) getColor(R.color.blue_main) else getColor(
                        R.color.defaultText
                    )
                )
                tvTabNotifi.setTextColor(
                    if (index == 3) getColor(R.color.blue_main) else getColor(
                        R.color.defaultText
                    )
                )

                imgHome.imageTintList =
                    getColorStateList(if (index == 0) R.color.blue_main else R.color.defaultText)
                imgFavorite.imageTintList =
                    getColorStateList(if (index == 1) R.color.blue_main else R.color.defaultText)
                imgTabCart.imageTintList =
                    getColorStateList(if (index == 2) R.color.blue_main else R.color.defaultText)
                imgNotifi.imageTintList =
                    getColorStateList(if (index == 3) R.color.blue_main else R.color.defaultText)
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() =
        if (binding.drawLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.user -> onStartActivity(R.id.user)
            R.id.order -> onStartActivity(R.id.order)
            R.id.setting -> onStartActivity(R.id.setting)
            R.id.sign_out -> onLogoutAccount()
        }
        binding.drawLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun onLogoutAccount() {

    }

    private fun onStartActivity(viewId: Int) {
        val intent = Intent(this@HomeActivity, DetailActivity::class.java)
        intent.putExtra(REPLACE_DRAWER, viewId)
        startActivity(intent)
    }
}