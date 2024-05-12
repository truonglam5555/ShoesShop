package com.example.shoesshop.features.admin.orders

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.FragmentOrdersManagerBinding
import com.example.shoesshop.model.BillOder
import com.example.shoesshop.model.ProductManager

class OrdersManagerFragment : BaseFragment<FragmentOrdersManagerBinding>() {

    private var andapterBill : AdapterBillManager = AdapterBillManager()
    override fun initAction() {

    }

    override fun initView() {
        setUpData()
        setListener()
    }

    fun setListener()
    {
        andapterBill.onclickItem = {
            val intent = Intent(this@OrdersManagerFragment.context, DetailBillodersActivity::class.java)
            intent.putExtra("idBill", it.id)
            startActivityForResult(intent,100)
        }
    }

    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOrdersManagerBinding
        get() = FragmentOrdersManagerBinding::inflate

    override fun onViewCreated() {

    }

    private fun setUpData() {
        binding.apply {
            recyclerView.adapter = andapterBill.apply {
                this.data = listProduct()
            }
        }
    }

    fun resetAdapter()
    {
        binding.recyclerView.adapter = andapterBill.apply {
            this.data = listProduct()
        }
    }

    private fun listProduct(): MutableList<BillOder> {
        val listData = mutableListOf<BillOder>()
        FetchDataFirebase.share.listBillOder.forEach {
            listData.add(it)
        }
        return listData
    }
}