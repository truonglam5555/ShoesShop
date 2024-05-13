package com.example.shoesshop.features.main.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseAdapter
import com.example.shoesshop.common.App
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.databinding.ItemSizeBinding
import javax.inject.Inject

class AdapterSize(val context:Context) : BaseAdapter<String, ItemSizeBinding>() {
    var subjectItem: ((obj: String) -> Unit)? = null
    private var selectedItemPosition = -1

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemSizeBinding
        get() = ItemSizeBinding::inflate

    override fun bindItem(item: String, binding: ItemSizeBinding, position: Int) {
        binding.run {
            tvSize.text = item
            cardView.setCardBackgroundColor(
                if (position == selectedItemPosition) ContextCompat.getColor(
                    App.app.applicationContext,
                    R.color.main_color
                ) else ContextCompat.getColor(context, R.color.white)
            )
            cardView.clickWithAnimationDebounce {
                selectedItemPosition = position
                notifyDataSetChanged()
                subjectItem?.invoke(item)
            }
        }
    }
}