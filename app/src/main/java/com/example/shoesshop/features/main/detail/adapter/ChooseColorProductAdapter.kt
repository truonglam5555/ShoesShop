package com.example.shoesshop.features.main.detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoesshop.R
import com.example.shoesshop.features.main.home.model.Category
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.ViewUtils.setMargins
import com.example.shoesshop.utils.ViewUtils.textColor
import java.util.concurrent.Executors

class ChooseColorProductAdapter(private val list: MutableList<Int>) :
    androidx.recyclerview.widget.ListAdapter<Int, ChooseColorProductAdapter.ViewHolder>(
        AsyncDifferConfig.Builder(ChooseColorProductDiffCallback())
            .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
            .build(),
    ) {
    //    private lateinit var binding: ItemCategoryBinding
    var onItemClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
//        binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context))
//        return ViewHolder(binding.root)

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_choose_color_product, parent, false)
        return ViewHolder(itemView)

//        itemView.layoutParams = ViewGroup.LayoutParams((DataShared.getWidth()!! / 4), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgProduct: ImageView = itemView.findViewById(R.id.img_product)

        @SuppressLint("SetTextI18n")
        fun onBind(menuItem: Int) {
            imgProduct.setImage(menuItem)
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(layoutPosition))
            }
        }
    }

}

class ChooseColorProductDiffCallback : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem

    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }
}