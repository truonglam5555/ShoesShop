package com.example.shoesshop.utils

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.widget.TextView
import com.example.shoesshop.R
import com.example.shoesshop.utils.ViewUtils.disableView
import com.example.shoesshop.utils.ViewUtils.enableView

object TextInputUtils {
    fun TextView.countdownTimer(textContent: String, action: () -> Unit = {}) {
        val timer = object : CountDownTimer(30000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished / 1000 >= 10)
                    text = "00:${millisUntilFinished / 1000}"
                else if (millisUntilFinished / 1000 < 10)
                    text = "00:0${millisUntilFinished / 1000}"
                disableView()
                setTextColor(resources.getColor(R.color.grey_3))
            }

            override fun onFinish() {
                text = textContent
                setTextColor(resources.getColor(R.color.main_color))
                enableView()
                action()
            }
        }
        timer.start()
    }
}