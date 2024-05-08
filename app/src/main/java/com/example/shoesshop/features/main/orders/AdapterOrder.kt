package com.example.shoesshop.features.main.orders

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseAdapter
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.databinding.ItemOrderBinding
import javax.inject.Inject

class AdapterOrder @Inject constructor(): BaseAdapter<Order, ItemOrderBinding>() {
    var subjectItem: ((obj: Order) -> Unit)? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemOrderBinding
        get() = ItemOrderBinding::inflate

    @SuppressLint("SetTextI18n")
    override fun bindItem(item: Order, binding: ItemOrderBinding, position: Int) {
        binding.run {
            tvProductName.text = item.nameOrder
            tvTotalProduct.text = item.totalProduct.toString().plus(" products")
            tvTotalPrice.text = "Total price: $${item.totalPrice}"
            itemShoe.clickWithAnimationDebounce {
                subjectItem?.invoke(item)
            }
        }

    }
}