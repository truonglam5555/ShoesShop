package com.example.shoesshop.features.main.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoesshop.R
import java.util.concurrent.Executors

class SearchAdapter :
    androidx.recyclerview.widget.ListAdapter<String, SearchAdapter.ViewHolder>(
        AsyncDifferConfig.Builder(SearchDiffCallback())
            .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
            .build(),
    ) {
    var onItemClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_search, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSearchName: TextView = itemView.findViewById(R.id.tv_search_name)

        @SuppressLint("StringFormatMatches")
        fun onBind(menuItem: String) {
            tvSearchName.text = menuItem

        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(layoutPosition))
            }
        }
    }

}

class SearchDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem

    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}