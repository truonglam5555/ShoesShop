package com.example.shoesshop.features.main.orders

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.FragmentOrdersBinding
import com.example.shoesshop.features.main.activity.HomeActivity
import com.example.shoesshop.model.BillOder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {

    @Inject
    lateinit var adapterOrder: AdapterOrder
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOrdersBinding
        get() = FragmentOrdersBinding::inflate

    override fun onViewCreated() {
        binding.layoutHeader.imgBack.clickWithAnimationDebounce {
            startActivity(Intent(requireContext(), HomeActivity::class.java))

        }
        binding.layoutHeader.tvTitle.text = "Your Orders"
    }

    override fun initAction() {
        adapterOrder.subjectItem ={
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, DetailOrderFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun initView() {
        binding.apply {
            recyclerView.adapter = adapterOrder.apply {
                this.data = listOrder()
            }
        }
    }

    private fun listOrder(): MutableList<Order> {
        //Data here
//        val listOrders = mutableListOf<Order>()
//        listOrders.add(Order(1, R.drawable._img_shoe_1,"Orders 1", 2, 200.0))

        val list = FetchDataFirebase.share.listBillOder
        val listUser: ArrayList<Order>  = ArrayList()
        if ( list.isNotEmpty())
        {

            val user = FetchDataFirebase.share.getCurrentUser()
            list.forEach {
                if (user.id == it.idUser)
                {
                    val image = FetchDataFirebase.share.getProductByID(it.listOder!!.first().idPruduct!!)!!.img_list!!.first()
                    listUser.add(Order(
                        it.id,
                        image,
                        it.id,
                        it.listOder!!.size,
                        it.listOder!!.map { it.total!! }.toList().sum().toDouble()
                    ))
                }
            }

        }
        return listUser
    }
}