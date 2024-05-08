package com.example.shoesshop.model

class BillOder(
    val id: String = "",
    val idUser:String = "",
    var timeOder: Long? = 0,
    var status: Int? = 0,
    var timeChangedStatus: Long? = 0,
    var shipCod: Int? = 0,
    var listOder: ArrayList<CardUser>? = ArrayList()
)