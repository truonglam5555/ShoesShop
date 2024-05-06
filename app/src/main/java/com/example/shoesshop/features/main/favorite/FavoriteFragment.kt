package com.example.shoesshop.features.main.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.R
import com.example.shoesshop.databinding.FragmentFavoriteBinding
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.data.fetch.KeyDataFireBase
import com.example.shoesshop.datastore.MySharedPreferences
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

    private var user : Employee? = null

    override fun onViewCreated() {
        getUser()
        initAdapter()
        click()
    }

    private fun getUser()
    {
        val idUser =  MySharedPreferences.shared.pullStringValue(KeyDataFireBase.keyUser)
        user = FetchDataFirebase.share.getEmployeeById(idUser!!)
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
            requireView().navigateTo(R.id.action_favoriteFragment_to_productDetailFragment)
        }
    }

    private fun initAdapter() {
        productAdapter = ProductAdapter()
        RecyclerViewUtils.initAdapter(
            mAdapter = productAdapter,
            rev = binding.layoutFav.revCommon,
            spanCount = 2,
            isSetScrollMiddle = false
        )

        val listProduct = ArrayList<Product>()
        FetchDataFirebase.share.listProduct.forEach {
            val isID =  getIdlikeById(it.id)
            if (isID != null)
            {
                listProduct.add(it)
            }
        }
        //productAdapter.submitList(homeViewModel.listProductt)
        productAdapter.submitList(listProduct)
        productAdapter.notifyDataSetChanged()
    }

    override fun initAction() {

    }

    override fun initView() {
        binding.layoutHeader.tvTitle.text = getString(R.string.text_favourite_title)
        binding.layoutHeader.icControl.setImage(R.drawable.ic_heart_circle_white)
        binding.layoutHeader.icControl.showView()
        binding.layoutFav.layoutTitleMenu2.hideView()

    }

    private fun getIdlikeById(iDD: Int): Int? {
        if (user!!.listLike != null && user!!.listLike!!.isNotEmpty())
        {
            for (id in user!!.listLike!!) {
                if (id == iDD) {
                    return id
                }
            }
        }

        return null
    }

}