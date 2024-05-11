package com.example.shoesshop.features.admin.product

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.databinding.FragmentProductManagerBinding
import com.example.shoesshop.databinding.FragmentProfileManagerBinding
import com.example.shoesshop.model.ProductManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductManagerFragment : BaseFragment<FragmentProductManagerBinding>() {

    @Inject
    lateinit var adapterProductManager: AdapterProductManager

    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductManagerBinding
        get() = FragmentProductManagerBinding::inflate

    override fun onViewCreated() {

    }

    override fun initAction() {

    }

    override fun initView() {
        setUpData()
    }

    private fun setUpData() {
        binding.apply {
            recyclerView.adapter = adapterProductManager.apply {
                this.data = listProduct()
            }
        }
    }

    private fun listProduct(): MutableList<ProductManager> {
        val listData = mutableListOf<ProductManager>()
        listData.add(ProductManager("Nike 200202", "https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/ca79d356-421f-4a96-a823-b695f15c7a34/in-season-tr-13-workout-shoes-BDTlPf.png",79.5))
        return listData
    }
}