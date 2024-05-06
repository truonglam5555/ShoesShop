package com.example.shoesshop

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class NoScrollViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {

    var fragments = ArrayList<Fragment>()
    var listTitles = ArrayList<String>()

    override fun getCount(): Int {
        return listTitles.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitles[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        listTitles.add(title)
    }
}