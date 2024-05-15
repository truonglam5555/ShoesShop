package com.example.shoesshop.features.main.cart

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.data.fetch.ActionCallback
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.data.fetch.KeyDataFireBase
import com.example.shoesshop.databinding.FragmentCartDetailBinding
import com.example.shoesshop.datastore.MySharedPreferences
import com.example.shoesshop.features.admin.HomeManagerActivity
import com.example.shoesshop.features.main.activity.HomeActivity
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.model.BillOder
import com.example.shoesshop.model.Employee
import com.example.shoesshop.widgets.dialog.BasePopupSuccessFragment


class CartDetailFragment : BaseFragment<FragmentCartDetailBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCartDetailBinding
        get() = FragmentCartDetailBinding::inflate

    var userData: Employee? = null
    var total: Int = 0;
    var shipCod: Int = 15;
    override fun onViewCreated() {
        binding.layoutNumber.icInfo.setImageResource(R.drawable.ic_call_phone)
        binding.layoutNumber.tvInfoName.text = "Phone"
        setListProduct()
    }

    private fun setListProduct() {
        // set list product here
    }

    fun getUserAndData() {
        val idUser = MySharedPreferences.shared.pullStringValue(KeyDataFireBase.keyUser)
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

    private fun getProduct(id: Int?): Product? {
        if (FetchDataFirebase.share.listProduct.isNotEmpty()) {
            FetchDataFirebase.share.listProduct.forEach {
                if (it.id == id) {
                    return it
                }
            }
        }
        return null
    }

    override fun initAction() {
        binding.layoutNumber.icEdit.clickWithAnimationDebounce {
            showDialogPhone()
        }
        binding.icEditAddress.clickWithAnimationDebounce {
            showDialogAddress()
        }
        val popupSuccess = BasePopupSuccessFragment(
            title = getString(R.string.text_your_payment_is_successful),
            btText = getString(R.string.text_back_to_shopping)
        )

        binding.layoutBottomCheckout.btCheckOut.setOnClickListener {
            val user = FetchDataFirebase.share.getCurrentUser()
            val curentTime = System.currentTimeMillis();
            val checkOut = BillOder(
                FetchDataFirebase.share.dataBillOder.push().key!!,
                user.id!!,
                curentTime,
                0,
                curentTime,
                shipCod,
                user.listCard!!
            )
            user.listCard = ArrayList()
            FetchDataFirebase.share.UpdateUser(user, object : ActionCallback {
                override fun onActionComplete(isSuccess: Boolean) {
                    if (isSuccess) {
                        FetchDataFirebase.share.addCheckOut(checkOut, object : ActionCallback {
                            override fun onActionComplete(isSuccess: Boolean) {
                                if (isSuccess) {
                                    val popupSuccess = BasePopupSuccessFragment(
                                        title = getString(R.string.text_your_payment_is_successful),
                                        btText = getString(R.string.text_back_to_shopping)
                                    )
                                    popupSuccess.show(childFragmentManager, "")

                                    popupSuccess.onCallback = {
                                        requireActivity().startActivity(
                                            Intent(
                                                requireActivity(),
                                                HomeActivity::class.java
                                            )
                                        )
                                    }
                                }
                            }

                        })
                    }
                }
            })
        }

        popupSuccess.onCallback = {
            requireActivity().startActivity(Intent(requireActivity(), HomeActivity::class.java))
        }
    }

    private fun showDialogAddress() {
        val dialog = showDialogCommon(R.layout.dialog_add_address)
        val btConfirm: Button = dialog.findViewById(R.id.bt_confirm)
        val edtAdress: EditText = dialog.findViewById(R.id.edt_adress)

        btConfirm.clickWithAnimationDebounce {
            val newAddress = edtAdress.text.toString()
            updatePhone(newAddress)
            dialog.dismiss()

        }
        dialog.show()
    }


    private fun showDialogPhone() {
        val dialog = showDialogCommon(R.layout.dialog_add_number)
        val btConfirm: Button = dialog.findViewById(R.id.bt_confirm)
        val etPhoneNumber: EditText = dialog.findViewById(R.id.edt_number)
        btConfirm.clickWithAnimationDebounce {
            val newPhoneNumber = etPhoneNumber.text.toString()
            updateAddress(newPhoneNumber)
            dialog.dismiss()
        }
        dialog.show()
    }
    private fun updatePhone(newName: String) {
        binding.layoutNumber.tvInfoType.text = newName
    }
    private fun updateAddress(newName: String) {
        binding.tvInfoType.text = newName
    }

    override fun initView() {
        getUserAndData()
        binding.layoutHeader.tvTitle.text = getString(R.string.text_my_cart)
        binding.layoutBottomCheckout.tvSubtotal.text = "$$total"
        binding.layoutBottomCheckout.tvDelivery.text = "$$shipCod"
        binding.layoutBottomCheckout.tvTotalCost.text = "$" + (total + shipCod)
    }

    private fun showDialogCommon(idLayoutDialog: Int): Dialog {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(idLayoutDialog)
        val window: Window? = dialog.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        val windowAttributes = window?.attributes
        if (windowAttributes != null) {
            windowAttributes.gravity = Gravity.CENTER
        }
        dialog.show()
        return dialog
    }
}