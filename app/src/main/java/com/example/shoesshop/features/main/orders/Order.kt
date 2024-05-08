package com.example.shoesshop.features.main.orders

import java.io.Serializable

data class Order(
    val id: Int,
    val image: Int,
    val nameOrder: String,
    val totalProduct: Int,
    val totalPrice: Double
) : Serializable
