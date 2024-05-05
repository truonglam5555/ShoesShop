package com.example.shoesshop.utils

import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.shoesshop.R


object ViewUtils {
    fun View.showView() {
        visibility = View.VISIBLE
    }

    fun View.hideView(isGone: Boolean = true) {
        visibility = if (isGone) {
            View.GONE
        } else {
            View.INVISIBLE
        }
    }

    fun View.disableView() {
        this.isEnabled = false
        this.alpha = 0.5F
    }

    fun View.enableView() {
        this.isEnabled = true
        this.alpha = 1F
    }

    fun View.navigateTo(des: Int) {
        startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.anim_in_left))
        val navBuilder = NavOptions.Builder()
        navBuilder.setEnterAnim(R.anim.anim_in_right).setExitAnim(R.anim.anim_out_left)
            .setPopEnterAnim(R.anim.anim_in_left).setPopExitAnim(R.anim.anim_out_right)
        findNavController().navigate(des, null, navBuilder.build())
    }

    fun TextView.textColor(color: Int){
        setTextColor(resources.getColor(color))
    }

    fun View.setMargins(
        left: Int = this.marginLeft,
        top: Int = this.marginTop,
        right: Int = this.marginRight,
        bottom: Int = this.marginBottom,
    ) {
        layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
            setMargins(left, top, right, bottom)
        }
    }
}