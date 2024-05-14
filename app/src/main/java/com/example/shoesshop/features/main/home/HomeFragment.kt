package com.example.shoesshop.features.main.home

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.constants.RecyclerValue
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.data.fetch.KeyDataFireBase
import com.example.shoesshop.databinding.FragmentHomeBinding
import com.example.shoesshop.datastore.MySharedPreferences
import com.example.shoesshop.features.main.activity.DetailActivity
import com.example.shoesshop.features.main.activity.HomeActivity
import com.example.shoesshop.features.main.home.adapter.CategoryAdapter
import com.example.shoesshop.features.main.home.adapter.ProductAdapter
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.model.CardUser
import com.example.shoesshop.model.Employee
import com.example.shoesshop.utils.RecyclerViewUtils
import com.example.shoesshop.utils.ViewUtils.hideView

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

companion object{
    const val REPLACE_FRAGMENT = "REPLACE_FRAGMENT"
}
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter


    private var user : Employee? = null


    override fun onViewCreated() {
        initAdapter()
        click()
        searchView()

    }

    private fun searchView() {
        binding.edtSearchView.clearFocus()
        binding.edtSearchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {

            }
        })
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
            //homeViewModel.product.value = it
            FetchDataFirebase.share.productSelect = it
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(HomeActivity.REPLACE_DRAWER, 1)
            startActivity(intent)

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
                    user!!.listCard!!.add(CardUser(product.id,product.sizes!!.first(),1))
                    FetchDataFirebase.share.dataUser.child(idUser).child(KeyDataFireBase.keyListCard).setValue(user!!.listCard!!)
                }
            }else if (user != null && user!!.listCard.isNullOrEmpty()){
                val  list = ArrayList<CardUser>()
                list.add(CardUser(product.id,product.sizes!!.first(),1))
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
    }

    override fun initView() {
        binding.layoutSelectCate.tvNext.hideView()

//        binding.layoutHeader.imgBack.hideView()
    }

    private fun filterProducts(type: String)
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
