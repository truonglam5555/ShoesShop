package com.example.shoesshop.features.main.notification.model

import com.example.shoesshop.features.main.home.model.Product

data class Notification(
    val id: Int = 0,
    val product: Product? = null,
    val time: String? = "",
    var isRead: Boolean = false
)
