package com.example.shoesshop.features.main.detail

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.constants.RecyclerValue
import com.example.shoesshop.data.fetch.ActionCallback
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.FragmentProductDetailBinding
import com.example.shoesshop.features.main.activity.HomeActivity
import com.example.shoesshop.features.main.detail.adapter.AdapterSize
import com.example.shoesshop.features.main.detail.adapter.ChooseColorProductAdapter
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.model.CardUser
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.RecyclerViewUtils
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {

    lateinit var adapterSize: AdapterSize

    var sizeSelect: String = ""
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductDetailBinding
        get() = FragmentProductDetailBinding::inflate

//    private lateinit var chooseColorProductAdapter: ChooseColorProductAdapter

    override fun onViewCreated() {
        adapterSize = AdapterSize(requireContext())
//        initAdapter()
//        click()
        binding.layoutChooseColor.layoutTitleMenu2.visibility = View.GONE

        if (FetchDataFirebase.share.productSelect != null) {
            setProduct(FetchDataFirebase.share.productSelect!!)
            adapterSize.subjectItem = {
                sizeSelect = it
            }
            binding.linLayout.setOnClickListener {
                if (sizeSelect.isNotEmpty()) {

                    val user = FetchDataFirebase.share.getCurrentUser()
                    if (user.listCard.isNullOrEmpty()) {
                        val listCard: ArrayList<CardUser> = ArrayList()
                        listCard.add(
                            CardUser(
                                FetchDataFirebase.share.productSelect!!.id,
                                sizeSelect.toDouble(),
                                1
                            )
                        )
                        user.listCard = listCard
                        FetchDataFirebase.share.UpdateUser(user, object : ActionCallback {
                            override fun onActionComplete(isSuccess: Boolean) {
                                if (isSuccess) {
                                    Toast.makeText(
                                        this@ProductDetailFragment.activity,
                                        "Add Success",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        })
                    } else {
                        user.listCard!!.forEach {
                            if (it.idPruduct == homeViewModel.product.value!!.id) {
                                it.total = it.total!! + 1;
                            }
                        }
                        FetchDataFirebase.share.UpdateUser(user, object : ActionCallback {
                            override fun onActionComplete(isSuccess: Boolean) {
                                if (isSuccess) {
                                    Toast.makeText(
                                        this@ProductDetailFragment.activity,
                                        "Add Success",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        })
                    }
                }
            }
        }

    }

    private fun setProduct(product: Product) {
        binding.tvName.text = product.name
        binding.tvType.text = product.type
        binding.tvPrice.text = "$${product.price}"
        binding.imgProduct.setImage(product.img_list!!.get(0))
        binding.tvDescription.text = product.description
        initSize(product.sizes!!)
    }

    private fun initSize(sizes: List<Double>) {
        binding.apply {
            recyclerViewSize.adapter = adapterSize.apply {
                //this.data = getData().toMutableList()
                this.data = sizes.map { it.toString() }.toMutableList()
            }
        }
    }

//    private fun initAdapter() {
//        ///// 3 imageView here
//        chooseColorProductAdapter = FetchDataFirebase.share.listProduct.forEach {
//            it.img_list
//        }
//        RecyclerViewUtils.initAdapter(
//            mAdapter = chooseColorProductAdapter,
//            rev = binding.layoutChooseColor.revCommon,
//            orientation = LinearLayoutManager.HORIZONTAL,
//            type = RecyclerValue.LINEAR_LAYOUT_MANAGER
//        )
//    }
//
//    private fun click() {
//        chooseColorProductAdapter.onItemClick = {
//            binding.imgProduct.setImage(it)
//        }
//    }

    override fun initAction() {
        binding.layoutHeader.imgBack.setOnClickListener {
            startActivity(Intent(requireContext(), HomeActivity::class.java))
        }
    }

    override fun initView() {
//        binding.layoutHeader.tvTitle.text = getString(R.string.text_sneaker_shop)
//        binding.layoutHeader.icControl.setImage(R.drawable.ic_shop)
//        binding.layoutChooseColor.layoutTitleMenu2.hideView()
//
//        homeViewModel.product.observe(viewLifecycleOwner) {
//            binding.imgProduct.setImage(it.image)
//            binding.tvName.text = it.name
//            binding.tvType.text = it.type
//            binding.tvPrice.text = "$ ${it.price}"
//            binding.tvDescription.text = it.description
//            chooseColorProductAdapter.submitList(it.img_list)
//            chooseColorProductAdapter.notifyDataSetChanged()
//        }
    }

}