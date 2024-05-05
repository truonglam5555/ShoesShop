package com.example.shoesshop.features.main.cart.view_model

import androidx.lifecycle.ViewModel
import com.example.shoesshop.features.main.cart.model.CartProduct
import com.example.shoesshop.features.main.home.model.Product

class ProductCartViewModel : ViewModel() {
    var listProductCartt: MutableList<CartProduct> = mutableListOf()

    fun getListProductCart(): MutableList<CartProduct> {
        listProductCartt.add(
            CartProduct(
                Product(id = 1, image = "", "Super shoe", 123.00),
                quantity = 1
            )
        )
        listProductCartt.add(
            CartProduct(
                Product(id = 1, image = "", "Super shoe", 123.00),
                quantity = 1
            )
        )
        listProductCartt.add(
            CartProduct(
                Product(id = 1, image = "", "Super shoe", 123.00),
                quantity = 1
            )
        )
        listProductCartt.add(
            CartProduct(
                Product(id = 1, image = "", "Super shoe", 123.00),
                quantity = 1
            )
        )
        listProductCartt.add(
            CartProduct(
                Product(id = 1, image = "", "Super shoe", 123.00),
                quantity = 1
            )
        )
        listProductCartt.add(
            CartProduct(
                Product(id = 1, image = "", "Super shoe", 123.00),
                quantity = 1
            )
        )
        listProductCartt.add(
            CartProduct(
                Product(id = 1, image = "", "Super shoe", 123.00),
                quantity = 1
            )
        )
        return listProductCartt
    }
}