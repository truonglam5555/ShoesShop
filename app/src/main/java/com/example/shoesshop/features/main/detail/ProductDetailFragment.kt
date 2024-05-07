package com.example.shoesshop.features.main.detail

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.databinding.FragmentProductDetailBinding
import com.example.shoesshop.features.main.activity.HomeActivity
import com.example.shoesshop.features.main.detail.adapter.AdapterSize
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.ViewUtils.hideView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {

    lateinit var adapterSize: AdapterSize
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductDetailBinding
        get() = FragmentProductDetailBinding::inflate

//    private lateinit var chooseColorProductAdapter: ChooseColorProductAdapter

    override fun onViewCreated() {
        adapterSize = AdapterSize(requireContext())
//        initAdapter()
//        click()
        //initSize()

    }

    fun setProduct(product: Product)
    {
        binding.tvName.text = product.name
        binding.tvType.text = product.type
        binding.tvPrice.text = product.price.toString()
        binding.imgProduct.setImage(product.img_list!!.get(0))
        initSize(product.sizes!!)
    }

    private fun initSize(sizes : List<Double>) {
        binding.apply {
            recyclerViewSize.adapter = adapterSize.apply {
                //this.data = getData().toMutableList()
                this.data = sizes.map { it.toString() }.toMutableList()
            }
        }
    }

    private fun getData(): List<String> {
        return listOf("38", "39", "40", "41", "42", "43")
    }

//    private fun initAdapter() {
//        chooseColorProductAdapter =
//            ChooseColorProductAdapter(homeViewModel.product.value?.img_list as MutableList<Int>)
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
        binding.layoutHeader.tvTitle.text = getString(R.string.text_sneaker_shop)
        binding.layoutHeader.icControl.setImage(R.drawable.ic_shop)
        binding.layoutChooseColor.layoutTitleMenu2.hideView()

        homeViewModel.product.observe(viewLifecycleOwner) {
            binding.imgProduct.setImage(it.image)
            binding.tvName.text = it.name
            binding.tvType.text = it.type
            binding.tvPrice.text = it.price.toString()
            binding.tvDescription.text = it.description
//            chooseColorProductAdapter.submitList(it.img_list)
//            chooseColorProductAdapter.notifyDataSetChanged()
        }
    }

}