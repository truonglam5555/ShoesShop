package com.example.shoesshop.features.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.constants.RecyclerValue
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.data.fetch.KeyDataFireBase
import com.example.shoesshop.databinding.FragmentHomeBinding
import com.example.shoesshop.datastore.MySharedPreferences
import com.example.shoesshop.features.main.home.adapter.CategoryAdapter
import com.example.shoesshop.features.main.home.adapter.ProductAdapter
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.model.CardUser
import com.example.shoesshop.model.Employee
import com.example.shoesshop.utils.RecyclerViewUtils
import com.example.shoesshop.utils.ViewUtils.hideView
import com.example.shoesshop.utils.ViewUtils.navigateTo
import com.google.firebase.database.DatabaseReference

class HomeFragment : BaseFragment<FragmentHomeBinding>() {


    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter


    private var user : Employee? = null


    override fun onViewCreated() {
        initAdapter()
        click()
    }

    fun getUser()
    {
        val idUser =  MySharedPreferences.shared.pullStringValue(KeyDataFireBase.keyUser)
        user = FetchDataFirebase.share.getEmployeeById(idUser!!)
    }

    private fun click() {
        categoryAdapter.onItemClick = {
            homeViewModel.clearCheckAllCategory()
            it.isChecked = true
            categoryAdapter.notifyDataSetChanged()
            //og.d("HomeFragment", "id ${it.id} isCheck = ${it.isChecked}")
            filterProducts(it.name)
        }

        productAdapter.onItemFaveClick = {
            it.isFav = !it.isFav
//            if (it.isFav) {
//                //handleLike(it)
//            }
            productAdapter.notifyDataSetChanged()
            handleLike(it)
        }
        productAdapter.onItemClick = {
            homeViewModel.product.value = it
            requireView().navigateTo(R.id.action_homeFragment_to_productDetailFragment)
        }
        productAdapter.onItemAddToCartClick = {
            val product = it;
            val idUser =  MySharedPreferences.shared.pullStringValue(KeyDataFireBase.keyUser)
            user = FetchDataFirebase.share.getEmployeeById(idUser!!)
            if (user != null && user!!.listCard != null && user!!.listCard!!.isNotEmpty())
            {
                var itemCard = getCardById(product.id)
                if (itemCard != null)
                {
                    user!!.listCard!!.forEach {
                        if (it.idPruduct == itemCard.idPruduct)
                        {
                            it.total = it.total!! +1;
                        }
                    }
                    FetchDataFirebase.share.dataUser.child(idUser).child(KeyDataFireBase.keyListCard).removeValue().let {
                        FetchDataFirebase.share.dataUser.child(idUser).child(KeyDataFireBase.keyListCard).setValue(user!!.listCard!!)
                    }
                }else{
                    user!!.listCard!!.add(CardUser(product.id,1))
                    FetchDataFirebase.share.dataUser.child(idUser).child(KeyDataFireBase.keyListCard).setValue(user!!.listCard!!)
                }
            }else if (user != null && user!!.listCard.isNullOrEmpty()){
                val  list = ArrayList<CardUser>()
                list.add(CardUser(product.id,1))
                FetchDataFirebase.share.dataUser.child(idUser).child(KeyDataFireBase.keyListCard).setValue(list)
            }
        }
    }


    private fun handleLike(item: Product)
    {
        val idUser =  MySharedPreferences.shared.pullStringValue(KeyDataFireBase.keyUser)
        user = FetchDataFirebase.share.getEmployeeById(idUser!!)
        if (user != null && user!!.listLike != null && user!!.listLike!!.isNotEmpty())
        {
            var idD = getIdlikeById(item.id)
            if (idD != null)
            {
                user!!.listLike!!.remove(idD)
                FetchDataFirebase.share.dataUser.child(idUser).child(KeyDataFireBase.keyListLike).removeValue().let {
                    FetchDataFirebase.share.dataUser.child(idUser).child(KeyDataFireBase.keyListLike).setValue(user!!.listLike!!)
                }
            }else{
                user!!.listLike!!.add(item.id)
                FetchDataFirebase.share.dataUser.child(idUser).child(KeyDataFireBase.keyListLike).setValue(user!!.listLike!!)
            }
        }else if (user != null && user!!.listLike.isNullOrEmpty()){
            val  list = ArrayList<Int>()
            list.add(item.id)
            FetchDataFirebase.share.dataUser.child(idUser).child(KeyDataFireBase.keyListLike).setValue(list)
        }
    }

    private fun getCardById(id: Int): CardUser? {
        for (card in user!!.listCard!!) {
            if (card.idPruduct == id) {
                return card
            }
        }
        return null
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

    override fun initAction() {
        binding.cardSearch.setOnClickListener {
            it.navigateTo(R.id.action_homeFragment_to_searchFragment)
        }
    }

    override fun initView() {
        binding.layoutSelectCate.tvNext.hideView()
        binding.layoutProduct.tvTitle.text = getString(R.string.text_popular_shoes_title)
//        binding.layoutHeader.imgBack.hideView()
    }

    fun filterProducts(type: String)
    {
        val  listFilter : ArrayList<Product> = ArrayList()

        FetchDataFirebase.share.listProduct.forEach {
            if (type =="All Shoes")
            {
                listFilter.add(it)
            }else if (it.type == type)
            {
                listFilter.add(it)
            }
        }
        productAdapter.submitList(listFilter)
        productAdapter.notifyDataSetChanged()
    }

    private fun initAdapter() {
        getUser()
        categoryAdapter = CategoryAdapter(homeViewModel.getListCategory())
        RecyclerViewUtils.initAdapter(
            mAdapter = categoryAdapter,
            rev = binding.layoutSelectCate.revCommon,
            orientation = LinearLayoutManager.HORIZONTAL,
            type = RecyclerValue.LINEAR_LAYOUT_MANAGER,
            isSetScrollMiddle = true
        )

        categoryAdapter.submitList(homeViewModel.listCate)
        categoryAdapter.notifyDataSetChanged()

        productAdapter = ProductAdapter()
        RecyclerViewUtils.initAdapter(
            mAdapter = productAdapter,
            rev = binding.layoutProduct.revCommon,
            spanCount = 2
        )
        val  list = FetchDataFirebase.share.listProduct //homeViewModel.getListProduct()
        list.forEach {
            it.isFav = getIdlikeById(it.id) != null
         }
        productAdapter.submitList(list)
        productAdapter.notifyDataSetChanged()
    }
}