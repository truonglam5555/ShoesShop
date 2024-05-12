package com.example.shoesshop.features.admin.product

import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.data.fetch.ActionCallback
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.ActivityAddProductBinding
import com.example.shoesshop.features.main.home.model.Product

class AddProductActivity :
    BaseActivity<ActivityAddProductBinding>(ActivityAddProductBinding::inflate) {

        private val listSize : List<Double> = ArrayList()
    val adapterSize: AdapterSizeProduct = AdapterSizeProduct()
    override fun onCreateView() {
        super.onCreateView()
        setListener()
    }

    private fun setAdapter()
    {
        val listData = mutableListOf<Double>()
        listSize.forEach {
            listData.add(it)
        }
        binding.apply {
            recyclerView.adapter = adapterSize.apply {
                this.data = listData
            }
        }
    }

    private fun setListener()
    {
        binding.btnAddProductDone.setOnClickListener {
            if (binding.edtProduct.editText!!.text.toString().isNotEmpty()&&
                binding.edtProductType.editText!!.text.isNotEmpty()&&
                binding.edtDiscription.editText!!.text.toString().isNotEmpty() && binding.edtPrice.editText!!.text.toString().isNotEmpty())
            {
                val  item = Product(
                    id = FetchDataFirebase.share.listProduct.size,
                    name = binding.edtProduct.editText!!.text.toString(),
                    image = "",
                    price = binding.edtPrice.editText!!.text.toString().toDouble(),
                    isFav = false,
                    description = binding.edtDiscription.editText!!.text.toString(),
                    isBestSeller = false,
                    type = binding.edtProductType.editText!!.text.toString(),
                    sizes = listSize
                )
                FetchDataFirebase.share.addProduct(item, object: ActionCallback{
                    override fun onActionComplete(isSuccess: Boolean) {
                        if(isSuccess)
                        {
                            setResult(101)
                            this@AddProductActivity.finish()
                        }else
                        {

                        }
                    }
                })
            }
        }
    }
}