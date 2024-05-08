package com.example.shoesshop.model

class BillOder(
    val id: String = "",
    val idUser:String,
    val timeOder: Long = 0,
    val status: Int = 0,
    val timeChangedStatus: Long = 0,
    val total: Int = 0,
    val shipCod: Int = 0,
    val listOder: ArrayList<CardUser> = ArrayList()
)