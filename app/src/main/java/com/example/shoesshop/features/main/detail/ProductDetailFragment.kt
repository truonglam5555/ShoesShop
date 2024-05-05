package com.example.shoesshop.features.main.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.constants.RecyclerValue
import com.example.shoesshop.databinding.FragmentProductDetailBinding
import com.example.shoesshop.features.main.detail.adapter.ChooseColorProductAdapter
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.RecyclerViewUtils
import com.example.shoesshop.utils.ViewUtils.hideView

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductDetailBinding
        get() = FragmentProductDetailBinding::inflate

    private lateinit var chooseColorProductAdapter: ChooseColorProductAdapter

    override fun onViewCreated() {
        initAdapter()
        click()
    }

    private fun initAdapter() {
        chooseColorProductAdapter =
            ChooseColorProductAdapter(homeViewModel.product.value?.img_list as MutableList<Int>)
        RecyclerViewUtils.initAdapter(
            mAdapter = chooseColorProductAdapter,
            rev = binding.layoutChooseColor.revCommon,
            orientation = LinearLayoutManager.HORIZONTAL,
            type = RecyclerValue.LINEAR_LAYOUT_MANAGER
        )

    }

    private fun click() {
        chooseColorProductAdapter.onItemClick = {
            binding.imgProduct.setImage(it)
        }
    }

    override fun initAction() {
        binding.layoutHeader.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initView() {
        binding.layoutHeader.tvTitle.text = getString(R.string.text_sneaker_shop)
        binding.layoutHeader.icControl.setImage(R.drawable.ic_shop)
        binding.layoutChooseColor.layoutTitleMenu2.hideView()

        homeViewModel.product.observe(viewLifecycleOwner){
            binding.imgProduct.setImage(it.image)
            binding.tvName.text = it.name
            binding.tvType.text = it.type
            binding.tvPrice.text = it.price.toString()
            binding.tvDescription.text = it.description
            chooseColorProductAdapter.submitList(it.img_list)
            chooseColorProductAdapter.notifyDataSetChanged()
        }
    }
}