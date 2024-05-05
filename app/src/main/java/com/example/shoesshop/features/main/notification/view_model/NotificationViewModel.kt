package com.example.shoesshop.features.main.notification.view_model

import androidx.lifecycle.ViewModel
import com.example.shoesshop.features.main.cart.model.CartProduct
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.features.main.notification.model.Notification

class NotificationViewModel: ViewModel() {
    var listNotification: MutableList<Notification> = mutableListOf()

    fun setNotification() {
        listNotification.add(
            Notification(
                id = 1,
                product = Product(id = 1, image = "", "Super shoe", 123.00),
                time = "7 mins ago",
                isRead = false
            )
        )
        listNotification.add(
            Notification(
                id = 1,
                time = "7 mins ago",
                product = Product(id = 1, image = "", "Super shoe", 123.00),
                isRead = true
            )
        )
        listNotification.add(
            Notification(
                id = 1,
                time = "7 mins ago",
                product = Product(id = 1, image = "", "Super shoe", 123.00),
                isRead = true
            )
        )
        listNotification.add(
            Notification(
                id = 1,
                time = "7 mins ago",
                product = Product(id = 1, image = "", "Super shoe", 123.00),
                isRead = true
            )
        )
        listNotification.add(
            Notification(
                id = 1,
                time = "72 mins ago",
                product = Product(id = 1, image = "", "Super shoe", 123.00),
                isRead = false
            )
        )
        listNotification.add(
            Notification(
                id = 1,
                time = "32 mins ago",
                product = Product(id = 1, image = "", "Super shoe", 123.00),
                isRead = false
            )
        )
        listNotification.add(
            Notification(
                id = 1,
                time = "7 mins ago",
                product = Product(id = 1, image = "", "Super shoe", 123.00),
                isRead = false
            )
        )
        listNotification.add(
            Notification(
                id = 1,
                product = Product(id = 1, image = "", "Super shoe", 123.00),
                isRead = false
            )
        )
        listNotification.add(
            Notification(
                id = 1,
                product = Product(id = 1, image = "", "Super shoe", 123.00),
                isRead = false
            )
        )

    }
}