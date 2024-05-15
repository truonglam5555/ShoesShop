package com.example.shoesshop.features.main.orders

import com.example.shoesshop.features.main.home.model.Product
import java.io.Serializable

data class Order(
    val id: String,
    val image: Int,
    val nameOrder: String,
    val totalProduct: Int,
    val totalPrice: Double
//    val listProduct:List<Product>
) : Serializable
