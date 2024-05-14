package com.example.shoesshop.features.admin.orders

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.data.fetch.ActionCallback
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.ActivityAddProductBinding
import com.example.shoesshop.databinding.ActivityDetailBillodersBinding
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.model.BillOder

class DetailBillodersActivity : BaseActivity<ActivityDetailBillodersBinding>(ActivityDetailBillodersBinding::inflate) ,ActionCallback {

    var idBill : String? = null

    lateinit var adapter : AdapterBillProducts

    val shipCost : Int = 50

    var subTotal : Int = 0

    var status : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView() {
        super.onCreateView()
        adapter = AdapterBillProducts(this)
        idBill = intent.getStringExtra("idBill")
        binding.tvTab.text = idBill
        setFiled()
        setAdapter()
        binding.btnBack.setOnClickListener {
            this.finish()
        }

        if (status ==2)
        {
           binding.btnConfirm.visibility = View.GONE
        }else
        {
            binding.btnConfirm.setOnClickListener{
                val bill = getBillById(idBill!!)
                if (status == 0)
                {
                    bill!!.status = 1
                    FetchDataFirebase.share.UpdateCheckOut(bill,this)
                } else if (status == 1) {
                    bill!!.status = 2
                    FetchDataFirebase.share.UpdateCheckOut(bill,this)
                }
            }
        }


    }

    private fun setAdapter()
    {
        val list = listProduct()
        binding.tvCountProduct.text = list.size.toString()
        binding.recyclerView.layoutParams.height = list.size * 330
        binding.recyclerView.adapter = adapter.apply {
            this.data = list
        }

        binding.tvSubtotalCost.text = "$$subTotal"
        binding.tvDeliveryCost.text = "$$shipCost"

        binding.tvTotalCost.text = "$"+ (subTotal + shipCost)
    }

    private fun listProduct(): MutableList<Product> {
        val listData = mutableListOf<Product>()

        val bill = getBillById(idBill!!)

        bill!!.listOder!!.forEach {
            val  pro= FetchDataFirebase.share.getProductByID(it.idPruduct!!)!!
            subTotal += ((it.total?.times(pro.price!!))?.toInt() ?: 0)
            listData.add(pro)
        }
        return listData
    }



    private fun setFiled()
    {
        if (idBill != null)
        {
            val item = getBillById(idBill!!)

            if (item != null)
            {
                val user = FetchDataFirebase.share.getEmployeeById(item.idUser)
                binding.tvUserName.text = user!!.fullName
                status = item.status!!
                if (status == 0)
                {
                    binding.cartPoint.setCardBackgroundColor(Color.parseColor("#F8E645"))
                    binding.tvStatus.text = "Pending"
                    binding.tvStatus.setTextColor(Color.parseColor("#F8E645"))
                    binding.tvConfig.text = "Confirm"

                }else  if (status == 1){
                    binding.cartPoint.setCardBackgroundColor(Color.parseColor("#16B726"))
                    binding.tvStatus.text = "Confirm"
                    binding.tvStatus.setTextColor(Color.parseColor("#16B726"))
                    binding.tvConfig.text = "Cancel"
                    binding.btnConfirm.setCardBackgroundColor(Color.parseColor("#FF4C24"))
                }else  if (status == 2){
                    binding.cartPoint.setCardBackgroundColor(Color.parseColor("#FF4C24"))
                    binding.tvStatus.text = "Cancel"
                    binding.tvStatus.setTextColor(Color.parseColor("#FF4C24"))
                }

            }
        }

    }

    private fun getBillById(id:String): BillOder?
    {
        FetchDataFirebase.share.listBillOder.forEach {
            if (id == it.id)
            {
                return  it
            }
        }

        return null
    }

    override fun onActionComplete(isSuccess: Boolean) {
        if (isSuccess)
        {
            setResult(100)
            this@DetailBillodersActivity.finish()
        }else{
            Toast.makeText(this@DetailBillodersActivity,"Upgrade Fail!",Toast.LENGTH_LONG).show()
        }
    }
}