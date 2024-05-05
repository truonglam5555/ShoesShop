package com.example.shoesshop.features.main.activity

import android.annotation.SuppressLint
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.databinding.ActivityMainBinding
import com.example.shoesshop.features.main.cart.CartFragment
import com.example.shoesshop.features.main.favorite.FavoriteFragment
import com.example.shoesshop.features.main.home.HomeFragment
import com.example.shoesshop.features.main.notification.NotificationFragment
import com.example.shoesshop.features.main.orders.OrdersFragment
import com.example.shoesshop.features.main.profile.ProfileFragment
import com.example.shoesshop.features.main.setting.SettingFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    NavigationView.OnNavigationItemSelectedListener {



    override fun onCreateView() {
        super.onCreateView()
        initViews()
    }

    private fun initViews() {
        binding.btOpen.clickWithAnimationDebounce{}
        binding.btOpen.setOnClickListener(this)
        binding.navView.setNavigationItemSelectedListener(this)
        replaceFragment(HomeFragment())
        binding.navView.bringToFront()
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.favorite -> replaceFragment(FavoriteFragment())
                R.id.notify -> replaceFragment(NotificationFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
            }
            true
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user -> replaceFragment(ProfileFragment())

            R.id.my_card -> replaceFragment(CartFragment())

            R.id.favorite -> replaceFragment(FavoriteFragment())

            R.id.order -> replaceFragment(OrdersFragment())

            R.id.notify -> replaceFragment(NotificationFragment())

            R.id.setting -> replaceFragment(SettingFragment())

            R.id.sign_out -> {
                //Log out
            }
        }
        binding.drawLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.fragmentContainerView, fragment)

        transaction.commit()
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() =
        if (binding.drawLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }

    override fun clickViews(view: View) {
        super.clickViews(view)
        if (view.id == R.id.bt_open) return binding.drawLayout.open()
    }
}