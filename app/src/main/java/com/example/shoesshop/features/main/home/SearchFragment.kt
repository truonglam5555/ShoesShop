package com.example.shoesshop.features.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.databinding.FragmentSearchBinding
import com.example.shoesshop.features.main.home.adapter.SearchAdapter
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.RecyclerViewUtils
import com.example.shoesshop.utils.ViewUtils.showView

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    private lateinit var searchAdapter: SearchAdapter

    override fun onViewCreated() {
        initAdapter()
        click()
    }
    private fun initAdapter(){
        searchViewModel.setSearchName()
        searchAdapter = SearchAdapter()
        RecyclerViewUtils.initAdapter(mAdapter = searchAdapter, rev = binding.layoutHistorySearch.revCommon)
        searchAdapter.submitList(searchViewModel.listSearchName)
    }
    private fun click(){
        searchAdapter.onItemClick = {
//            logic here
        }
    }

    override fun initAction() {
        binding.layoutHeader.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.layoutHeader.tvEnd.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initView() {
        binding.layoutHeader.tvEnd.text = getString(R.string.text_cancel)
        binding.layoutHeader.tvEnd.showView()
        binding.layoutHeader.tvTitle.text = getString(R.string.text_search)
        binding.layoutHeader.imgBack.setImage(R.drawable.ic_back_auth)
    }

}