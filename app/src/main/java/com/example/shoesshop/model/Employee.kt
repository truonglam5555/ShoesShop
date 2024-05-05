package com.example.shoesshop.model

class Employee(
    var id: String? = null,
    var fullName: String? = null,
    var birthDay: Int? = null,
    var rule: ArrayList<Int>? = null,
    var pathImage: String? = null,
    var email: String? = null,
    var pass: String? = null,
    val isadmid : Int = 0,
    var  listCard : ArrayList<CardUser>? = null,
    var  listLike : ArrayList<Int>? = null
)