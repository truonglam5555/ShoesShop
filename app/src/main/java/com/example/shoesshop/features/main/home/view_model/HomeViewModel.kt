package com.example.shoesshop.features.main.home.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoesshop.R
import com.example.shoesshop.features.main.home.model.Category
import com.example.shoesshop.features.main.home.model.Product

class HomeViewModel : ViewModel() {
    var listCate: MutableList<Category> = mutableListOf()
    var listProductt: MutableList<Product> = mutableListOf()
    var listProductFav: MutableList<Product> = mutableListOf()

    var product = MutableLiveData<Product>()

    fun getListCategory(): MutableList<Category> {
        listCate.add(Category(1, "All Shoes", true))
        listCate.add(Category(2, "Outdoor", false))
        listCate.add(Category(3, "Tennis", false))
        listCate.add(Category(4, "Sports", false))
        listCate.add(Category(5, "Sneaker", false))
        return listCate
    }

    fun clearCheckAllCategory() {
        for (i in listCate.indices) {
            listCate[i].isChecked = false
        }
    }

}