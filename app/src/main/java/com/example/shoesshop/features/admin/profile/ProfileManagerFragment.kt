package com.example.shoesshop.features.admin.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.databinding.FragmentProfileManagerBinding
import com.example.shoesshop.features.admin.product.DetailProductManagerActivity
import com.example.shoesshop.model.Employee
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileManagerFragment : BaseFragment<FragmentProfileManagerBinding>() {

    var adapterUserManager: AdapterUserManager = AdapterUserManager()
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileManagerBinding
        get() = FragmentProfileManagerBinding::inflate

    override fun onViewCreated() {
    }

    override fun initAction() {
        adapterUserManager.onClickDeleteItem = {
            //delete here
        }
        adapterUserManager.onClickItem = {
            val intent = Intent(requireContext(), DetailUserManagerActivity::class.java)
            intent.putExtra("idUser", it)
            startActivity(intent)
        }
    }

    override fun initView() {
        binding.apply {
            recyclerView.adapter = adapterUserManager.apply {
                this.data = getData()
            }
        }
    }

    private fun getData(): MutableList<Employee> {
        val listData = mutableListOf<Employee>()
        return listData
    }
}