package com.example.shoesshop.features.main.notification.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoesshop.R
import com.example.shoesshop.features.main.notification.model.Notification
import com.example.shoesshop.utils.ImageUtils.setImage
import java.util.concurrent.Executors

class NotificationAdapter :
    androidx.recyclerview.widget.ListAdapter<Notification, NotificationAdapter.ViewHolder>(
        AsyncDifferConfig.Builder(NotificationDiffCallback())
            .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
            .build(),
    ) {
    var onItemClick: ((Notification) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgProduct: ImageView = itemView.findViewById(R.id.img_product)
        val tvProductName: TextView = itemView.findViewById(R.id.tv_product_name)
        val tvPrice1: TextView = itemView.findViewById(R.id.tv_price_1)
        val tvPrice2: TextView = itemView.findViewById(R.id.tv_price_2)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val layoutBgItem: ConstraintLayout = itemView.findViewById(R.id.bg_item)

        @SuppressLint("StringFormatMatches")
        fun onBind(menuItem: Notification) {
            tvProductName.text = menuItem.product?.name
            tvPrice1.text = itemView.resources.getString(R.string.text_price, menuItem.product?.price)
            tvPrice2.text = itemView.resources.getString(R.string.text_price, menuItem.product?.price)
            tvTime.text = menuItem.time
            imgProduct.setImage(menuItem.product?.image)

            if (!menuItem.isRead){
                tvProductName.setTextColor(itemView.resources.getColor(R.color.main_color))
                layoutBgItem.setBackgroundColor(itemView.resources.getColor(R.color.white))
            }else{
                tvProductName.setTextColor(itemView.resources.getColor(R.color.black_1))
                layoutBgItem.setBackgroundColor(itemView.resources.getColor(R.color.transparent))
            }

        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(layoutPosition))
            }
        }
    }

}

class NotificationDiffCallback : DiffUtil.ItemCallback<Notification>() {
    override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem.id == newItem.id
    }
}