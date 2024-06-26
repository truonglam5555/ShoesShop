package com.example.shoesshop.features.admin.product

import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.ActivityDetailProductManagerBinding
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.model.ProductManager

class DetailProductManagerActivity :
    BaseActivity<ActivityDetailProductManagerBinding>(ActivityDetailProductManagerBinding::inflate) {

    var idProduct: Int = -1
    val adapterSize: AdapterSizeProduct = AdapterSizeProduct()
    lateinit var adapterImage: Adapterphotodetail
    lateinit var product: Product
    override fun onCreateView() {
        super.onCreateView()
        adapterImage = Adapterphotodetail(this)
        idProduct = intent.getIntExtra("idProduct", -1)
        if (idProduct >= 0) {
            product = FetchDataFirebase.share.getProductByID(idProduct)!!
            setListener()
            setField()
            setAdapter()
        }
    }

    private fun setAdapter() {
        val listData = mutableListOf<Double>()
        product.sizes?.forEach {
            listData.add(it)
        }


        binding.apply {
            recyclerViewPhto.adapter = adapterImage.apply {
                if (product.img_listString != null) {
                    this.data = product.img_listString!!.toMutableList()
                }
            }
            recyclerView.adapter = adapterSize.apply {
                this.data = listData
            }
        }
    }

    private fun setField() {

        binding.etNamePro.setText(product.name)
        binding.etTypePro.setText(product.type)
        binding.etDisPro.setText(product.description)
        binding.etPrice.setText(product.price.toString())

    }

    private fun setListener() {
        binding.btnBack.setOnClickListener {
            this.finish()
        }
    }
}