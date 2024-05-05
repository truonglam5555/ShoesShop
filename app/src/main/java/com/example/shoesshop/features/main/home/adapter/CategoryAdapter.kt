package com.example.shoesshop.features.main.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoesshop.R
import com.example.shoesshop.databinding.ItemCategoryBinding
import com.example.shoesshop.features.main.home.model.Category
import com.example.shoesshop.utils.ViewUtils.setMargins
import com.example.shoesshop.utils.ViewUtils.textColor
import java.util.concurrent.Executors

class CategoryAdapter(private val list: MutableList<Category>) :
    androidx.recyclerview.widget.ListAdapter<Category, CategoryAdapter.ViewHolder>(
        AsyncDifferConfig.Builder(CategoryDiffCallback())
            .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
            .build(),
    ) {
    //    private lateinit var binding: ItemCategoryBinding
    var onItemClick: ((Category) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
//        binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context))
//        return ViewHolder(binding.root)

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(itemView)

//        itemView.layoutParams = ViewGroup.LayoutParams((DataShared.getWidth()!! / 4), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardCate: CardView = itemView.findViewById(R.id.card_category)
        private val tvCate: TextView = itemView.findViewById(R.id.tv_category)

        @SuppressLint("SetTextI18n")
        fun onBind(menuItem: Category) {
            tvCate.text = menuItem.name
            if (layoutPosition == 0) {
                cardCate.setMargins(
                    left = itemView.resources.getDimension(R.dimen.spacing_13).toInt(),
                    right = itemView.resources.getDimension(R.dimen.spacing_7).toInt()
                )
            } else if (layoutPosition != 0 && layoutPosition != list.size - 1) {
                cardCate.setMargins(
                    left = itemView.resources.getDimension(R.dimen.spacing_7).toInt(),
                    right = itemView.resources.getDimension(R.dimen.spacing_7).toInt()
                )
            } else {
                cardCate.setMargins(
                    left = itemView.resources.getDimension(R.dimen.spacing_7).toInt(),
                    right = itemView.resources.getDimension(R.dimen.spacing_13).toInt()
                )
            }

            if (menuItem.isChecked) {
                cardCate.setCardBackgroundColor(itemView.resources.getColor(R.color.main_color))
                tvCate.textColor(R.color.white)
            } else {
                cardCate.setCardBackgroundColor(itemView.resources.getColor(R.color.white))
                tvCate.textColor(R.color.grey_1)
            }

        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(layoutPosition))
            }
        }
    }

}

class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name
    }
}