package com.example.shoesshop.model

import java.io.Serializable

data class ProductManager(
    val idPro : Int,
    val name: String,
    val urlImage: String,
    val price: Double
) : Serializable
