package com.example.shoesshop.features.main.home.view_model

import androidx.lifecycle.ViewModel
import com.example.shoesshop.features.main.home.model.Category

class SearchViewModel: ViewModel() {
    var listSearchName: MutableList<String> = mutableListOf()

    fun setSearchName(){
        listSearchName.add("New Shoes")
        listSearchName.add("Nike Top Shoes")
        listSearchName.add("Nike Air Force")
        listSearchName.add("Shoes")
        listSearchName.add("Snakers Nike Shoes")
        listSearchName.add("Regular Shoes")
        listSearchName.add("New Shoes")
        listSearchName.add("New Shoes")
    }
}