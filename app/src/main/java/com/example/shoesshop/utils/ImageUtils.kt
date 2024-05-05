package com.example.shoesshop.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.shoesshop.R

object ImageUtils {
    fun ImageView.setImage(url: String?){
        Glide.with(context).load(url).placeholder(R.drawable.img_placeholder).into(this)
    }

    fun ImageView.setImage(id: Int?){
        Glide.with(context).load(id).placeholder(R.drawable.img_placeholder).into(this)
    }
}