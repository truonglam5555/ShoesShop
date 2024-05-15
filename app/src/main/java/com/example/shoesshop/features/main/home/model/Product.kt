package com.example.shoesshop.features.main.home.model

import java.io.Serializable

open class Product(
    val id: Int = 0,
    val image: String? = "",
    val name: String? = "",
    val price: Double? = null,
    var isFav: Boolean = false,
    var img_list: List<Int>? = null,
    var img_listString: List<String>? = null,
    val description: String? = "",
    val type: String? = "",
    var sizes: List<Double>? = null,
    val isBestSeller: Boolean? = false,
): Serializable
