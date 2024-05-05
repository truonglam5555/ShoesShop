package com.example.shoesshop.features.main.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoesshop.R
import com.example.shoesshop.features.main.home.model.Category
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.ViewUtils.hideView
import com.example.shoesshop.utils.ViewUtils.setMargins
import com.example.shoesshop.utils.ViewUtils.showView
import com.example.shoesshop.utils.ViewUtils.textColor
import java.util.concurrent.Executors

class ProductAdapter :
    androidx.recyclerview.widget.ListAdapter<Product, ProductAdapter.ViewHolder>(
        AsyncDifferConfig.Builder(ProductDiffCallback())
            .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
            .build(),
    ) {
    //    private lateinit var binding: ItemCategoryBinding
    var onItemClick: ((Product) -> Unit)? = null
    var onItemAddToCartClick: ((Product) -> Unit)? = null
    var onItemFaveClick: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
//        binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context))
//        return ViewHolder(binding.root)

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(itemView)

//        itemView.layoutParams = ViewGroup.LayoutParams((DataShared.getWidth()!! / 4), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icHeart: ImageView = itemView.findViewById(R.id.ic_heart)
        private val imgProduct: ImageView = itemView.findViewById(R.id.img_product)
        private val tvBestSeller: TextView = itemView.findViewById(R.id.tv_best_seller)
        private val tvProductName: TextView = itemView.findViewById(R.id.tv_product_name)
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        private val btAddToCart: LinearLayout = itemView.findViewById(R.id.bt_add_to_cart)

        @SuppressLint("StringFormatMatches")
        fun onBind(menuItem: Product) {
            tvProductName.text = menuItem.name
            tvPrice.text = itemView.resources.getString(R.string.text_price, menuItem.price)
            imgProduct.setImage(menuItem.image)
            if (layoutPosition % 2 == 0) {
                itemView.setMargins(
                    left = itemView.resources.getDimension(R.dimen.spacing_13).toInt(),
                    right = itemView.resources.getDimension(R.dimen.spacing_7).toInt()
                )
            } else {
                itemView.setMargins(
                    left = itemView.resources.getDimension(R.dimen.spacing_7).toInt(),
                    right = itemView.resources.getDimension(R.dimen.spacing_13).toInt()
                )
            }

            if (menuItem.isFav){
                icHeart.setImage(R.drawable.ic_heart_active)
            }else{
                icHeart.setImage(R.drawable.ic_heart_deactive)
            }

            if (menuItem.isBestSeller == true) {
                tvBestSeller.showView()
            } else {
                tvBestSeller.hideView(false)
            }

        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(layoutPosition))
            }
            btAddToCart.setOnClickListener {
                onItemAddToCartClick?.invoke(getItem(layoutPosition))
            }
            icHeart.setOnClickListener {
                onItemFaveClick?.invoke(getItem(layoutPosition))
            }
        }
    }

}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name
    }
}