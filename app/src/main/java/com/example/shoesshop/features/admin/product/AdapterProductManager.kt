package com.example.shoesshop.features.admin.product

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoesshop.base.BaseAdapter
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.databinding.ItemProductManagerBinding
import com.example.shoesshop.model.ProductManager
import javax.inject.Inject

class AdapterProductManager @Inject constructor() : BaseAdapter<ProductManager, ItemProductManagerBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemProductManagerBinding
        get() = ItemProductManagerBinding::inflate
    var subjectRemove: ((obj: ProductManager) -> Unit)? = null
    var subjectItem: ((obj: ProductManager) -> Unit)? = null

    override fun bindItem(item: ProductManager, binding: ItemProductManagerBinding, position: Int) {
        binding.run {
            imgShoes.setImageURI(Uri.parse(item.urlImage))
            tvPrice.text = "$${item.price}"
            tvName.text = item.name
            icRemove.clickWithAnimationDebounce {
                subjectRemove?.invoke(item)
            }
            cardViewItem.clickWithAnimationDebounce {
                subjectItem?.invoke(item)
            }
        }
    }
}