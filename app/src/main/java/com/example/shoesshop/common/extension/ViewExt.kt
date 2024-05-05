package com.example.shoesshop.common.extension

import android.os.SystemClock
import android.view.View
import com.example.shoesshop.common.extension.scaleClick.PushDownAnim

fun View.clickWithAnimationDebounce(
    debounceTime: Long = 250L,
    scale: Float = 0.95f,
    action: () -> Unit
) {
    PushDownAnim.setPushDownAnimTo(this)
        .setScale(PushDownAnim.MODE_SCALE, scale)
        .clickWithAnimationDebounce(object : View.OnClickListener {
            private var lastClickTime: Long = 0
            override fun onClick(v: View) {
                if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
                else action()
                lastClickTime = SystemClock.elapsedRealtime()
            }
        })
}