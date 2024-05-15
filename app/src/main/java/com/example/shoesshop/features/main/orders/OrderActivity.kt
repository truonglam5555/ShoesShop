package com.example.shoesshop.features.main.orders

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.ActivityOrderBinding
import com.example.shoesshop.features.main.activity.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderActivity : BaseActivity<ActivityOrderBinding>(ActivityOrderBinding::inflate){

    @Inject
    lateinit var adapterOrder: AdapterOrder

    override fun onCreateView() {
        super.onCreateView()
        binding.layoutHeader.imgBack.clickWithAnimationDebounce {
            startActivity(Intent(this@OrderActivity, HomeActivity::class.java))

        }
        binding.layoutHeader.tvTitle.text = "Your Orders"
        initAction()
        initView()
    }

    fun initAction() {
        adapterOrder.subjectItem ={
          val intent = Intent(this@OrderActivity, DetailOrderActivity::class.java)
            intent.putExtra("order", it)
            startActivity(intent)
        }
    }

    fun initView() {
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
