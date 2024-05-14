package com.example.shoesshop.features.admin

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.example.shoesshop.NoScrollViewPagerAdapter
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.databinding.ActivityHomeManagerBinding
import com.example.shoesshop.features.admin.home.HomeManagerFragment
import com.example.shoesshop.features.admin.orders.OrdersManagerFragment
import com.example.shoesshop.features.admin.product.ProductManagerFragment
import com.example.shoesshop.features.admin.profile.ProfileManagerFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeManagerActivity :
    BaseActivity<ActivityHomeManagerBinding>(ActivityHomeManagerBinding::inflate) {

    companion object {
        const val REPLACE_DRAWER = "REPLACE_DRAWER"
    }

    private lateinit var adapterCollection: NoScrollViewPagerAdapter
    private val frmHome by lazy { HomeManagerFragment() }
    private val frmOrders by lazy { OrdersManagerFragment() }
    private val frmProduct by lazy { ProductManagerFragment() }
    private val frmProfile by lazy { ProfileManagerFragment() }

    override fun onCreateView() {
        super.onCreateView()
        initViews()
    }

    private fun initViews() {
        initViewPager()
        listenerView()
    }

    private fun listenerView() {
        binding.run {
            viewTabHome.clickWithAnimationDebounce {
                if (viewPager.currentItem != 0) viewPager.setCurrentItem(0, true)
                imgHome.setImageResource(R.drawable.ic_home_unchecked)
            }
            viewTabFavorite.clickWithAnimationDebounce {
                if (viewPager.currentItem != 1) viewPager.setCurrentItem(1, true)
//                frmFav.refreshAdepter()
                imgFavorite.setImageResource(R.drawable.ic_product)
            }
            viewTabCart.clickWithAnimationDebounce {
                if (viewPager.currentItem != 2) viewPager.setCurrentItem(2, true)
//                frmCart.refreshCard()
                imgTabCart.setImageResource(R.drawable.ic_orders)
            }
            viewTabNotifi.clickWithAnimationDebounce {
                if (viewPager.currentItem != 3) viewPager.setCurrentItem(3, true)
                imgProfile.setImageResource(R.drawable.ic_profile_not_choose)
            }
        }
    }

    private fun initViewPager() {
        adapterCollection = NoScrollViewPagerAdapter(supportFragmentManager)
        adapterCollection.apply {
            addFragment(frmHome, "Home")
            addFragment(frmProduct, "Product")
            addFragment(frmOrders, "Orders")
            addFragment(frmProfile, "Profile")
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
                tvTabProfile.setTextColor(
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
                imgProfile.imageTintList =
                    getColorStateList(if (index == 3) R.color.blue_main else R.color.defaultText)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 100)
        {
            frmHome.resetCount()
            frmOrders.resetAdapter()
        }
        if (resultCode == 101)
        {
            frmProduct.resetAdapter()
        }
    }

}
