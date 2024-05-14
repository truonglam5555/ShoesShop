package com.example.shoesshop.features.admin.product

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.data.fetch.ActionCallback
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.FragmentProductManagerBinding
import com.example.shoesshop.databinding.FragmentProfileManagerBinding
import com.example.shoesshop.features.admin.orders.DetailBillodersActivity
import com.example.shoesshop.model.ProductManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductManagerFragment  : BaseFragment<FragmentProductManagerBinding>() {

    @Inject
    lateinit var adapterProductManager: AdapterProductManager

    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductManagerBinding
        get() = FragmentProductManagerBinding::inflate

    override fun onViewCreated() {

    }

    override fun initAction() {
        adapterProductManager.context = this.requireContext()
        adapterProductManager.subjectRemove ={
            val item = FetchDataFirebase.share.getProductByID(it.idPro)
           FetchDataFirebase.share.deleteProducts(item = item!!,object :ActionCallback{
               override fun onActionComplete(isSuccess: Boolean) {
                   if (isSuccess)
                   {
                       resetAdapter()
                   }else{
                       Toast.makeText(requireContext(),"Delete Product Fail!",Toast.LENGTH_LONG).show()
                   }
               }
           })
        }
        adapterProductManager.subjectItem ={
            val intent = Intent(requireContext(), DetailProductManagerActivity::class.java)
            intent.putExtra("idProduct", it.idPro)
            startActivityForResult(intent,101)
        }
        binding.btnAddProduct.setOnClickListener{
            val intent = Intent(requireContext(), AddProductActivity::class.java)
            startActivityForResult(intent,101)
        }
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

    fun resetAdapter()
    {
        binding.recyclerView.adapter = adapterProductManager.apply {
            this.data = listProduct()
        }
    }

    private fun listProduct(): MutableList<ProductManager> {
        val listData = mutableListOf<ProductManager>()
        FetchDataFirebase.share.listProduct.forEach {
            listData.add(ProductManager(it.id,it.name!!, if (!it.img_listString.isNullOrEmpty())  it.img_listString!!.first() else "https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/ca79d356-421f-4a96-a823-b695f15c7a34/in-season-tr-13-workout-shoes-BDTlPf.png" ,it.price!!))
        }
        return listData
    }
}