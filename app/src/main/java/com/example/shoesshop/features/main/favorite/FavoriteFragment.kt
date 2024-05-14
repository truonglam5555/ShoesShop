package com.example.shoesshop.features.main.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.R
import com.example.shoesshop.databinding.FragmentFavoriteBinding
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.data.fetch.KeyDataFireBase
import com.example.shoesshop.datastore.MySharedPreferences
import com.example.shoesshop.features.main.activity.DetailActivity
import com.example.shoesshop.features.main.activity.HomeActivity
import com.example.shoesshop.features.main.home.HomeFragment
import com.example.shoesshop.features.main.home.adapter.ProductAdapter
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.model.Employee
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.RecyclerViewUtils
import com.example.shoesshop.utils.ViewUtils.hideView
import com.example.shoesshop.utils.ViewUtils.navigateTo
import com.example.shoesshop.utils.ViewUtils.showView

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteBinding
        get() = FragmentFavoriteBinding::inflate

    private lateinit var productAdapter: ProductAdapter
    val listProduct = ArrayList<Product>()

    private var user: Employee? = null

    override fun onViewCreated() {
        getUser()
        initAdapter()
        click()
    }

    private fun getUser() {
        val idUser = MySharedPreferences.shared.pullStringValue(KeyDataFireBase.keyUser)
        user = FetchDataFirebase.share.getEmployeeById(idUser!!)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshAdepter() {
        getUser()
        val updatedListProduct = mutableListOf<Product>()

        FetchDataFirebase.share.listProduct.forEach {
            val isID = getIdlikeById(it.id)
            if (isID != null && !updatedListProduct.contains(it)) {
                updatedListProduct.add(it)
            }
        }
        listProduct.clear()
        listProduct.addAll(updatedListProduct)
        productAdapter.notifyDataSetChanged()
    }

    private fun click() {
        productAdapter.onItemFaveClick = {
            it.isFav = !it.isFav
            if (it.isFav) {
                //Logic here
            }
            productAdapter.notifyDataSetChanged()
        }
        productAdapter.onItemClick = {
            homeViewModel.product.value = it
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(HomeActivity.REPLACE_DRAWER, 1)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initAdapter() {
        productAdapter = ProductAdapter()
        RecyclerViewUtils.initAdapter(
            mAdapter = productAdapter,
            rev = binding.layoutFav.revCommon,
            spanCount = 2,
            isSetScrollMiddle = false
        )

        val updatedListProduct = mutableListOf<Product>()

        FetchDataFirebase.share.listProduct.forEach {
            val isID = getIdlikeById(it.id)
            if (isID != null && !updatedListProduct.contains(it)) {
                updatedListProduct.add(it)
            }
        }
        listProduct.clear()
        listProduct.addAll(updatedListProduct)
        productAdapter.submitList(listProduct)
        productAdapter.notifyDataSetChanged()


    }

    override fun initAction() {

    }

    override fun initView() {
        binding.layoutFav.layoutTitleMenu2.hideView()
//        if (listProduct.size > 0) {
//            binding.notFound.hideView(false)
//        } else {
//            binding.notFound.hideView()
//        }
    }

    private fun getIdlikeById(iDD: Int?): Int? {
        var iDItem : Int? = null
        if (user?.listLike != null && !user?.listLike.isNullOrEmpty()) {
            user?.listLike?.forEach {
                if (it == iDD) {
                    iDItem = it
                }
            }
        }
        return iDItem
    }

}