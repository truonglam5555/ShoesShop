package com.example.shoesshop.features.admin.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseAdapter
import com.example.shoesshop.common.App
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.databinding.ItemSizeBinding
import javax.inject.Inject

class AdapterManagerSize @Inject constructor() : BaseAdapter<String, ItemSizeBinding>() {
    private var selectedItemPosition = -1
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemSizeBinding
        get() = ItemSizeBinding::inflate

    override fun bindItem(item: String, binding: ItemSizeBinding, position: Int) {
        binding.run {
            tvSize.text = item
            if (position == selectedItemPosition) {
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        App.app.applicationContext,
                        R.color.main_color
                    )
                )
                cardView.clickWithAnimationDebounce {
                    selectedItemPosition = position
                    notifyDataSetChanged()
//                    subjectItem?.invoke(item)
                }
            }
        }
    }
}