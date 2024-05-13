package com.example.shoesshop.features.admin.product

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.shoesshop.R
import com.example.shoesshop.common.App
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import javax.inject.Inject


class AdapterPhoto(val context:Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var subjectAddImg: ((number: Int) -> Unit)? = null
    var subjectCallBackDeletePosition: ((position: Int) -> Unit)? = null
    var subjectCallBackChoosePosition: ((position: Int) -> Unit)? = null
    private val VIEW_TYPE_IMAGE = 1
    private val VIEW_TYPE_DEFAULT_ICON = 2
    private var listImg: MutableList<String> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun init(list: MutableList<String>) {
        listImg.clear()
        listImg.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_IMAGE -> {
                val view =
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_photo, parent, false)
                ImageViewHolder(view)
            }

            VIEW_TYPE_DEFAULT_ICON -> {
                val view =
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_default, parent, false)
                DefaultIconViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_IMAGE -> {
                val imageHolder = holder as ImageViewHolder
                if (listImg.size >= 1) {
//                    Glide.with(context)
//                        .load(listImg[position])
//                        .into(imageHolder.imageCenter)
                    setRoundedCornerImage(imageHolder.imageCenter, listImg[position])

                    imageHolder.imageCenter.clickWithAnimationDebounce {
                        subjectCallBackChoosePosition?.invoke(position)
                    }

                    imageHolder.imgClose.clickWithAnimationDebounce {
                        subjectCallBackDeletePosition?.invoke(position)
                    }
                }
            }

            VIEW_TYPE_DEFAULT_ICON -> {
                val iconHolder = holder as DefaultIconViewHolder
                iconHolder.imageAdd.clickWithAnimationDebounce {
                    subjectAddImg?.invoke(0)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listImg.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (listImg.isEmpty() || position == 0) {
            VIEW_TYPE_DEFAULT_ICON
        } else {
            VIEW_TYPE_IMAGE
        }
    }

    private fun setRoundedCornerImage(imageView: ImageView, imageUrl: String) {
        val radius = 10
        val requestOptions = RequestOptions().transform(RoundedCorners(radius))

        Glide.with(context)
            .load(imageUrl)
            .apply(requestOptions)
            .into(imageView)
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageCenter: ImageView = itemView.findViewById(R.id.img_photo)
        val imgClose: ImageView = itemView.findViewById(R.id.ic_remove)
    }

    class DefaultIconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageAdd: ImageView = itemView.findViewById(R.id.imgLabelItem)
    }
}
