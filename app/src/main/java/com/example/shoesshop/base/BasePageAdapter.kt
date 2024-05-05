package com.example.shoesshop.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class BasePageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = arrayListOf<Fragment>()

    fun addFragment(vararg fragments: Fragment) {
        this.fragments.addAll(fragments)
    }

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

}