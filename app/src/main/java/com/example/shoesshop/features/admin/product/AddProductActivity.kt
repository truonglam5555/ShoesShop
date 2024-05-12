package com.example.shoesshop.features.admin.product

import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.data.fetch.ActionCallback
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.ActivityAddProductBinding
import com.example.shoesshop.features.main.home.model.Product

class AddProductActivity :
    BaseActivity<ActivityAddProductBinding>(ActivityAddProductBinding::inflate) {
    override fun onCreateView() {
        super.onCreateView()
    }

    fun addProduct()
    {
        val  item = Product(
            id = FetchDataFirebase.share.listProduct.size,
            name = "",
            image = "",
            price = 0.0,
            isFav = false, // thêm vào size và ....
        )
        FetchDataFirebase.share.addProduct(item, object: ActionCallback{
            override fun onActionComplete(isSuccess: Boolean) {
               if(isSuccess)
               {
                   // to do
                   // reset before Page
               }else
               {
                  // .....
               }
            }
        })
    }
}