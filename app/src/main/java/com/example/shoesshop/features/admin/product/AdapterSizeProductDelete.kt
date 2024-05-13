package com.example.shoesshop.features.admin.product

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseAdapter
import com.example.shoesshop.databinding.ItemProductManagerBinding
import com.example.shoesshop.databinding.ItemSizeBinding
import com.example.shoesshop.databinding.ItemSizeDeleteBinding
import com.example.shoesshop.model.ProductManager
import javax.inject.Inject

class AdapterSizeProductDelete  @Inject constructor() : BaseAdapter<Double, ItemSizeDeleteBinding>() {

    var sizeRemove: ((obj: Double) -> Unit)? = null
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemSizeDeleteBinding
        get() = ItemSizeDeleteBinding::inflate

    override fun bindItem(item: Double, binding: ItemSizeDeleteBinding, position: Int) {
        binding.tvSize.text = item.toString()
        binding.btnDelete.setOnClickListener {
            sizeRemove?.invoke(item)
        }
    }
}