package com.example.shoesshop.features.admin.orders

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoesshop.base.BaseAdapter
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.ItemOrderBillBinding
import com.example.shoesshop.databinding.ItemProductManagerBinding
import com.example.shoesshop.model.BillOder
import com.example.shoesshop.model.ProductManager
import com.example.shoesshop.utils.ViewUtils.textColor
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AdapterBillManager @Inject constructor() : BaseAdapter<BillOder, ItemOrderBillBinding>() {

    var onclickItem: ((obj: BillOder) -> Unit)? = null
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemOrderBillBinding
        get() = ItemOrderBillBinding::inflate

    override fun bindItem(item: BillOder, binding: ItemOrderBillBinding, position: Int) {
        val userOder = FetchDataFirebase.share.getEmployeeById(item.idUser)
        binding.tvUserName.text = userOder!!.fullName
        binding.tvUserEmail.text = userOder.email
        binding.tvIdBill.text = userOder.id
        val formattedDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(item.timeChangedStatus!!), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        binding.tvDatetime.text = formattedDateTime
        if (item.status == 0)
        {
            binding.cartPoint.setCardBackgroundColor(Color.parseColor("#F8E645"))
            binding.tvStatus.text = "Pending"
            binding.tvStatus.setTextColor(Color.parseColor("#F8E645"))
        }else  if (item.status == 1){
            binding.cartPoint.setCardBackgroundColor(Color.parseColor("#16B726"))
            binding.tvStatus.text = "Confirm"
            binding.tvStatus.setTextColor(Color.parseColor("#16B726"))
        }else  if (item.status == 2){
            binding.cartPoint.setCardBackgroundColor(Color.parseColor("#FF4C24"))
            binding.tvStatus.text = "Cancel"
            binding.tvStatus.setTextColor(Color.parseColor("#FF4C24"))
        }
        binding.itemBillOder.setOnClickListener {
            onclickItem?.invoke(item)
        }
    }
}