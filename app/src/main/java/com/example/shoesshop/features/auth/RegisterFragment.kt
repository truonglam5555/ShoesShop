package com.example.shoesshop.features.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.data.fetch.ActionCallback
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.data.fetch.KeyDataFireBase
import com.example.shoesshop.databinding.FragmentLoginBinding
import com.example.shoesshop.databinding.FragmentRegisterBinding
import com.example.shoesshop.model.CardUser
import com.example.shoesshop.model.Employee
import com.example.shoesshop.utils.ImageUtils.setImage
import com.google.firebase.database.DatabaseReference
import java.time.LocalDateTime

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        get() = FragmentRegisterBinding::inflate

    override fun onViewCreated() {

    }

    override fun initAction() {
        binding.layoutHeader.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvGoBackLogin.setOnClickListener {
            findNavController().popBackStack()
        }
        listenner();
    }

    override fun initView() {
        binding.layoutHeader.imgBack.setImage(R.drawable.ic_back_auth)
        binding.layoutTitleAuthScreen.tvHeading.text = getString(R.string.text_register_account)
        binding.layoutTitleAuthScreen.tvDescription.text = getString(R.string.text_description_register)
    }

    fun  listenner()
    {
        binding.btLogin.setOnClickListener{
            FetchDataFirebase.share.auth.createUserWithEmailAndPassword(binding.layoutEmail.edtEmail.text.toString(),binding.layoutPassword.edtPassword.text.toString()).addOnSuccessListener {
                if (it.user !=null)
                {
                    val user =  it.user
                    user?.sendEmailVerification().let {
                        val list = ArrayList<Int>()
                        list.add(1) //  Đọc
                        list.add(0) // Viết
                        list.add(0) // Xoá => tuỳ bác muốn set vị trí
                        val item = Employee(FetchDataFirebase.share.dataUser.push().key!!,binding.layoutName.edtName.text.toString(),
                            0,list,"",user?.email,binding.layoutPassword.edtPassword.text.toString())
                        FetchDataFirebase.share.addUser(item,object : ActionCallback{
                            override fun onActionComplete(isSuccess: Boolean) {
                                if (isSuccess)
                                {
                                    Toast.makeText(this@RegisterFragment.activity,"Sucesss!",Toast.LENGTH_SHORT).show().let {
                                        //EmailController.shared.sendMail("Register Account","Thanks You Register Account!",binding.layoutEmail.edtEmail.text.toString())
                                        findNavController().popBackStack()
                                    }

                                }else
                                {
                                    Toast.makeText(this@RegisterFragment.activity,"Fail!",Toast.LENGTH_SHORT).show()
                                }
                            }
                        })
                    }
                }
            }.addOnFailureListener{
                Toast.makeText(this@RegisterFragment.activity,it.message,Toast.LENGTH_SHORT).show()
            }
//            if (binding.layoutEmail.edtEmail.text.toString().isNotEmpty()) return@setOnClickListener
//            val item = FetchDataFirebase.share.getEmployeeByEmail(binding.layoutEmail.edtEmail.text.toString())
//            if (item == null)
//            {
////
//            }
        }
    }
}