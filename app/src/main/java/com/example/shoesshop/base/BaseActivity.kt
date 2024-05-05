package com.example.shoesshop.base

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatEditText
import androidx.viewbinding.ViewBinding
import com.example.shoesshop.R
import com.google.android.material.textfield.TextInputEditText

open class BaseActivity<VB : ViewBinding>(
    val bindingInflater: (LayoutInflater) -> VB
) : AppCompatActivity(), View.OnClickListener {
    val binding: VB by lazy { bindingInflater(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI(binding.root)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

//        display cutout
        val attrib = window.attributes
        // It never gets here
        attrib.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        onCreateView()
    }

    open fun onCreateView() {

    }

    final override fun onClick(view: View) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
        clickViews(view)
    }

    protected open fun clickViews(view: View) {
        // do nothing
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupUI(view: View) {
        // Set up touch listener for non-text box views to hide keyboard.
//        if (view !is EditText || view !is TextInputEditText) {
        view.setOnTouchListener { _, _ ->
            hideSoftKeyboard(this@BaseActivity)
            false
        }
//        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken,
                0
            )
        }
    }

}