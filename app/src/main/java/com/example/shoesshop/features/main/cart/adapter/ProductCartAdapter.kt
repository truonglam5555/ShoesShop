package com.example.shoesshop.features.main.cart.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoesshop.R
import com.example.shoesshop.common.OnSwipeTouchListener
import com.example.shoesshop.features.main.cart.model.CartProduct
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.ViewUtils.hideView
import com.example.shoesshop.utils.ViewUtils.showView
import java.util.concurrent.Executors

class ProductCartAdapter :
    androidx.recyclerview.widget.ListAdapter<CartProduct, ProductCartAdapter.ViewHolder>(
        AsyncDifferConfig.Builder(ProductCartDiffCallback())
            .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
            .build(),
    ) {

    var onItemClick: ((CartProduct) -> Unit)? = null
    var onItemAddButtonClick: ((CartProduct) -> Unit)? = null
    var onItemSubButtonClick: ((CartProduct) -> Unit)? = null
    var onItemDeleteButtonClick: ((CartProduct) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icAdd: ImageView = itemView.findViewById(R.id.img_add_product)
        val icSub: ImageView = itemView.findViewById(R.id.img_sub_product)
        val imgProduct: ImageView = itemView.findViewById(R.id.img_product)
        val tvQuantity: TextView = itemView.findViewById(R.id.tv_quantity)
        val tvProductName: TextView = itemView.findViewById(R.id.tv_product_name)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        val layoutDelete: LinearLayout = itemView.findViewById(R.id.layout_delete)
        val layoutQuantity: LinearLayout = itemView.findViewById(R.id.layout_quantity)
        val itemShoe: LinearLayout = itemView.findViewById(R.id.item_shoe)

        @SuppressLint("StringFormatMatches")
        fun onBind(menuItem: CartProduct) {
            tvProductName.text = menuItem.product?.name
            tvPrice.text =
                itemView.resources.getString(R.string.text_price, menuItem.product?.price)
            imgProduct.setImage(menuItem.product?.image)
            tvQuantity.text=menuItem.quantity.toString()
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(layoutPosition))
            }
            layoutDelete.setOnClickListener {
                onItemDeleteButtonClick?.invoke(getItem(layoutPosition))
            }
            icAdd.setOnClickListener {
                onItemAddButtonClick?.invoke(getItem(layoutPosition))
            }
            icSub.setOnClickListener {
                onItemSubButtonClick?.invoke(getItem(layoutPosition))
            }

            itemView.setOnLongClickListener{
                layoutDelete.hideView()
                layoutQuantity.hideView()
                true
            }

//            itemView.getOnSwipe()
            imgProduct.getOnSwipe()
            tvProductName.getOnSwipe()
            tvPrice.getOnSwipe()
            itemShoe.getOnSwipe()

        }
        private fun View.getOnSwipe(){
            setOnTouchListener(object : OnSwipeTouchListener(context){
                override fun onSwipeTop(){
                    layoutDelete.hideView()
                    layoutQuantity.hideView()
                }
                override fun onSwipeRight(){
                    Log.d("ProductAdapter", "Swipe right")
                    layoutDelete.hideView()
                    layoutQuantity.showView()
                }
                override fun onSwipeLeft(){
                    Log.d("ProductAdapter", "Swipe Left")
                    layoutDelete.showView()
                    layoutQuantity.hideView()
                }
                override fun onSwipeBottom(){
                    layoutDelete.hideView()
                    layoutQuantity.hideView()
                }
            })
        }
    }


}

class ProductCartDiffCallback : DiffUtil.ItemCallback<CartProduct>() {
    override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
        return oldItem.product?.id == newItem.product?.id

    }

    override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
        return oldItem.product?.id == newItem.product?.id && oldItem.product?.name == newItem.product?.name
    }
}