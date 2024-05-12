package com.example.shoesshop.features.admin.orders

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseAdapter
import com.example.shoesshop.databinding.ItemBillProductBinding
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.utils.ImageUtils.setImage
import javax.inject.Inject

class AdapterBillProducts (context :  Context): BaseAdapter<Product, ItemBillProductBinding>() {

    lateinit var context: Context
    init {
        this.context = context
    }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemBillProductBinding
        get() = ItemBillProductBinding::inflate

    @SuppressLint("StringFormatMatches")
    override fun bindItem(item: Product, binding: ItemBillProductBinding, position: Int) {
        binding.tvProductName.text = item.name
        binding.imgProduct.setImage(item.img_list!!.first())
        binding.tvPrice.text = context.resources.getString(R.string.text_price, item.price) // item.price.toString()
    }
}