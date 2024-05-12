package com.example.shoesshop.features.admin.product

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseAdapter
import com.example.shoesshop.databinding.ItemProductManagerBinding
import com.example.shoesshop.databinding.ItemSizeBinding
import com.example.shoesshop.model.ProductManager
import javax.inject.Inject

class AdapterSizeProduct  @Inject constructor() : BaseAdapter<Double, ItemSizeBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemSizeBinding
        get() = ItemSizeBinding::inflate

    override fun bindItem(item: Double, binding: ItemSizeBinding, position: Int) {
        binding.tvSize.text = item.toString()
    }
}