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

    fun getListProduct(): MutableList<Product> {
        listProductt.add(
            Product(
                1, "", "Nike Jordan 1", 302.00, false, isBestSeller = true, img_list = listOf(
                    R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
                )
            )
        )
        listProductt.add(
            Product(
                2, "", "Nike Jordan 2", 302.00, false, isBestSeller = true, img_list = listOf(
                    R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
                )
            )
        )
        listProductt.add(
            Product(
                3, "", "Nike Jordan 3", 302.00, false, isBestSeller = true, img_list = listOf(
                    R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
                )
            )
        )
        listProductt.add(
            Product(
                4, "", "Nike Jordan 4", 302.00, false, isBestSeller = true, img_list = listOf(
                    R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
                )
            )
        )
        listProductt.add(
            Product(
                5, "", "Nike Jordan 5", 302.00, false, isBestSeller = true, img_list = listOf(
                    R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
                )
            )
        )
        listProductt.add(
            Product(
                6, "", "Nike Jordan 6", 302.00, false, isBestSeller = true, img_list = listOf(
                    R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
                )
            )
        )
        return listProductt
    }

    fun clearCheck(id: Int) {
        for (i in listCate.indices)
            if (listCate[i].id != id)
                listCate[i].isChecked = false
    }

    fun clearCheckAllCategory() {
        for (i in listCate.indices) {
            listCate[i].isChecked = false
        }
    }

}