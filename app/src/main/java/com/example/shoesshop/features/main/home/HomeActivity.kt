package com.example.shoesshop.features.main.home

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.databinding.ActivityHomeBinding
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.ViewUtils.hideView
import com.example.shoesshop.utils.ViewUtils.showView
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle

class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    private lateinit var headerView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBottomNavigation()
        initDrawer()
    }

    private fun setupBottomNavigation() {
        val navController = findNavController(R.id.fragmentContainerView)

        binding.layoutMemberId.setOnClickListener {
            try {
                it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_in_left))
                val navBuilder = NavOptions.Builder()
                navBuilder.setEnterAnim(R.anim.anim_in_right).setExitAnim(R.anim.anim_out_left)
                    .setPopEnterAnim(R.anim.anim_in_left).setPopExitAnim(R.anim.anim_out_right)
                (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController.navigate(
                    R.id.cartFragment,null, navBuilder.build()
                )
//                it.navigateTo(R.id.cartFragment)
            } catch (_: Exception) {
            }
        }


        binding.layoutNav.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_home -> {
                    try {
                        navController.popBackStack()
                        (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController.navigate(
                            R.id.homeFragment,
                        )
                    } catch (_: Exception) {
                    }
                }

                R.id.radio_fav -> {
                    try {
                        navController.popBackStack()
                        (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController.navigate(
                            R.id.favoriteFragment,
                        )
                    } catch (_: Exception) {
                    }
                }

                R.id.radio_notification -> {
                    try {
                        navController.popBackStack()
                        (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController.navigate(
                            R.id.notificationFragment,
                        )
                    } catch (_: Exception) {
                    }
                }

                R.id.radio_account -> {
                    try {
                        navController.popBackStack()
                        (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController.navigate(
                            R.id.profileFragment,
                        )
                    } catch (_: Exception) {
                    }
                }

                else -> {
                }
            }
        }
//        Set hide, show menu
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment ->{
                    binding.layoutNav.showView()
                    binding.toolbar.showView()
                }
                R.id.favoriteFragment,
                R.id.notificationFragment,
                R.id.profileFragment,
                -> {
                    binding.layoutNav.showView()
                }

                else -> {
                    binding.layoutNav.hideView()
                    binding.toolbar.hideView()
                }
            }
        }

    }

    private fun initDrawer() {
        val drawerToggle = DuoDrawerToggle(this,binding.drawer,binding.toolbar,
            0,
            0
        )

        binding.drawer.setDrawerListener(drawerToggle)
        drawerToggle.syncState()

//        val contentView: View = binding.drawer.contentView
//        val menuView: View = binding.drawer.menuView

//        textH = menuView.findViewById(R.id.hello)
//        textH.setOnClickListener(this)
        supportActionBar?.setHomeButtonEnabled(false);
        supportActionBar?.setDisplayShowHomeEnabled(false);
        supportActionBar?.setIcon(R.drawable.ic_nav_drawer); //also displays wide logo
        supportActionBar?.setDisplayShowTitleEnabled(false); //optional

        headerView = binding.navView.getHeaderView(0).findViewById(R.id.imageView)!!

        val nameHeader: TextView = binding.navView.getHeaderView(0).findViewById(R.id.tv_header_name)
        nameHeader.text = "Emmanuel Oyiboke"
        nameHeader.setTextColor(resources.getColor(R.color.white))
        headerView.setImage(R.drawable.img_placeholder)


        headerView.setOnClickListener {
            try {
//logic here
            } catch (e: Exception) {
            }
        }



    }
}