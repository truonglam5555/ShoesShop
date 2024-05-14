package com.example.shoesshop.features.admin.product

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.shoesshop.base.BaseAdapter
import com.example.shoesshop.databinding.ItemPhotoBinding
import com.example.shoesshop.databinding.ItemSizeBinding
import javax.inject.Inject

class Adapterphotodetail( context: Context)  : BaseAdapter<String, ItemPhotoBinding>() {

    lateinit var  contextx: Context
    init {
        contextx = context
    }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemPhotoBinding
        get() = ItemPhotoBinding::inflate

    override fun bindItem(item: String, binding: ItemPhotoBinding, position: Int) {

        val radius = 10
        val requestOptions = RequestOptions().transform(RoundedCorners(radius))

        Glide.with(contextx)
            .load(item)
            .apply(requestOptions)
            .into( binding.imgPhoto)
    }

}