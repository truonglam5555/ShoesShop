package com.example.shoesshop.features.main.cart.model

import com.example.shoesshop.features.main.home.model.Product

data class CartProduct(
    val product: Product? = null,
    var quantity: Int?,
)
