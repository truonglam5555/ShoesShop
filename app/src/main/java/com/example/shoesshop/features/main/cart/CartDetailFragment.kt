package com.example.shoesshop.features.main.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.data.fetch.ActionCallback
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.data.fetch.KeyDataFireBase
import com.example.shoesshop.databinding.FragmentCartDetailBinding
import com.example.shoesshop.datastore.MySharedPreferences
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.model.BillOder
import com.example.shoesshop.model.Employee
import com.example.shoesshop.widgets.dialog.BasePopupSuccessFragment


class CartDetailFragment : BaseFragment<FragmentCartDetailBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCartDetailBinding
        get() = FragmentCartDetailBinding::inflate

    var userData: Employee? = null
    var total : Int =0;
    var shipCod : Int = 50;
    override fun onViewCreated() {

    }

    fun getUserAndData()
    {
        val idUser =  MySharedPreferences.shared.pullStringValue(KeyDataFireBase.keyUser)
        userData = FetchDataFirebase.share.getEmployeeById(idUser!!)
        if (userData?.listCard != null) {
            userData?.listCard!!.forEach {
                val pro = getProduct(it.idPruduct)
                if (pro != null) {
                    total = (total + (it.total!!.times(pro.price!!))).toInt()
                }
            }
        }
    }

    private fun getProduct( id : Int?) :Product?  {
        if (FetchDataFirebase.share.listProduct.isNotEmpty())
        {
            FetchDataFirebase.share.listProduct.forEach {
                if (it.id == id)
                {
                    return it
                }
            }
        }

        return  null
    }

    override fun initAction() {

        val popupSuccess = BasePopupSuccessFragment(
            title = getString(R.string.text_your_payment_is_successful),
            btText = getString(R.string.text_back_to_shopping)
        )

        binding.layoutHeader.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.layoutBottomCheckout.btCheckOut.setOnClickListener {
            val curentTime = System.currentTimeMillis();
            val checkOut = BillOder(FetchDataFirebase.share.dataBillOder.push().key!!, curentTime,0,curentTime,total,shipCod,userData!!.listCard!!)
            FetchDataFirebase.share.addCheckOut(checkOut,object : ActionCallback{
                override fun onActionComplete(isSuccess: Boolean) {
                    if (isSuccess)
                    {
                        popupSuccess.show(childFragmentManager,"")
                    }
                }

            })
        }

        popupSuccess.onCallback = {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

    override fun initView() {
        getUserAndData()
        binding.layoutHeader.tvTitle.text = getString(R.string.text_my_cart)
        binding.layoutBottomCheckout.tvSubtotal.text = "$$total"
        binding.layoutBottomCheckout.tvDelivery.text = "$$shipCod"
        binding.layoutBottomCheckout.tvTotalCost.text ="$" + (total + shipCod)
    }
}