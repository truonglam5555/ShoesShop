package com.example.shoesshop.data.fetch

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class KeyDataFireBase {
    companion object{
        val keyUser ="user"
        val keyAdmin ="admin"
        val keyListCard = "listCard"
        val keyListLike = "listLike"
        val keyProduct = "products"
        val keyBillOders = "billoders"
        val keyChat = "chats"
    }
}